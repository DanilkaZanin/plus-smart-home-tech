package ru.program.aggregator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.program.aggregator.service.EventService;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private final EventService eventService;
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
            } finally {
                log.info("Закрываем консюмер и продюсер");
                consumer.close();
                eventService.shutdown();
            }
        }
    }

    private void handleRecord(ConsumerRecord<String, SensorEventAvro> consumerRecord) {
        Optional<SensorsSnapshotAvro> snapshotAvro = eventService.updateState(consumerRecord.value());
        snapshotAvro.ifPresent(eventService::sendToKafka);
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
