package ru.program.aggregator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.program.aggregator.service.EventService;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private final EventService eventService;
    private final Producer<String, SpecificRecordBase> producer;
    private final Consumer<String, SensorEventAvro> consumer;
    private final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    @Value("${kafka.telemetry-sensors-topic}")
    private String telemetrySensorsTopic;

    @Value("${kafka.telemetry-snapshots-topic}")
    private String telemetrySnapshotTopic;

    @Value("${kafka.consumer.timeout-ms}")
    private long consumeTimeoutMs;

    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));

        try {
            consumer.subscribe(List.of(telemetrySensorsTopic));
            while (true) {
                ConsumerRecords<String, SensorEventAvro> records = consumer.poll(Duration.ofMillis(consumeTimeoutMs));
                int count = 0;

                for (ConsumerRecord<String, SensorEventAvro> consumerRecord : records) {
                    handleRecord(consumerRecord);
                    manageOffsets(consumerRecord, count);
                    count++;
                }

                consumer.commitAsync();
            }

        } catch (WakeupException ignored) {
            // shutdown
        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {
            try {
                consumer.commitSync(currentOffsets);
                producer.flush();
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
                log.info("Закрываем продюсер");
                producer.close();
            }
        }
    }

    private void handleRecord(ConsumerRecord<String, SensorEventAvro> consumerRecord) {
        Optional<SensorsSnapshotAvro> snapshotAvro = eventService.updateState(consumerRecord.value());
        snapshotAvro.ifPresent(snapshot -> {
            log.info("Получили снимок состояния: {}", snapshot);
            ProducerRecord<String, SpecificRecordBase> message = new ProducerRecord<>(
                    telemetrySnapshotTopic,
                    null,
                    snapshot.getTimestamp().toEpochMilli(),
                    snapshot.getHubId(),
                    snapshot
            );
            producer.send(message);
        });
    }

    private void manageOffsets(ConsumerRecord<String, SensorEventAvro> consumerRecord, int count) {
        currentOffsets.put(
                new TopicPartition(consumerRecord.topic(), consumerRecord.partition()),
                new OffsetAndMetadata(consumerRecord.offset() + 1)
        );

        if (count % 10 == 0) {
            consumer.commitAsync(currentOffsets, (offsets, exception) -> {
                if (exception != null) {
                    log.warn("Ошибка при фиксации оффсетов: {}", offsets, exception);
                }
            });
        }
    }
}
