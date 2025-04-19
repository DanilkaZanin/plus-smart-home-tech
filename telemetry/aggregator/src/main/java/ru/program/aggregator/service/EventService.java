package ru.program.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final Producer<String, SpecificRecordBase> producer;

    @Value("${kafka.config.sensor-snapshots-topic}")
    private String sensorSnapshotTopic;

    private final Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    public Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event) {
        SensorsSnapshotAvro snapshot = snapshots.getOrDefault(
                event.getHubId(),
                createSensorSnapshotAvro(event.getHubId())
        );

        SensorStateAvro oldState = snapshot.getSensorsState().get(event.getId());

        if (oldState != null &&
                (oldState.getTimestamp().isAfter(event.getTimestamp()) ||
                        oldState.getData().equals(event.getPayload()))) {
            return Optional.empty();
        }

        SensorStateAvro newState = createSensorStateAvro(event);
        snapshot.getSensorsState().put(event.getId(), newState);
        snapshot.setTimestamp(event.getTimestamp());
        snapshots.put(event.getHubId(), snapshot);

        return Optional.of(snapshot);
    }

    public void sendToKafka(SensorsSnapshotAvro snapshot) {
        ProducerRecord<String, SpecificRecordBase> producerRecord = new ProducerRecord<>(
                sensorSnapshotTopic,
                null,
                snapshot.getTimestamp().toEpochMilli(),
                snapshot.getHubId(),
                snapshot
        );

        producer.send(producerRecord);
    }

    public void shutdown() {
        log.info("Shutting down EventService, closing Kafka producer...");
        if (producer != null) {
            producer.close();
        }
    }

    private SensorsSnapshotAvro createSensorSnapshotAvro(String hubId) {
        return SensorsSnapshotAvro.newBuilder()
                .setHubId(hubId)
                .setTimestamp(Instant.now())
                .setSensorsState(new HashMap<>())
                .build();
    }

    private SensorStateAvro createSensorStateAvro(SensorEventAvro event) {
        return SensorStateAvro.newBuilder()
                .setTimestamp(event.getTimestamp())
                .setData(event.getPayload())
                .build();
    }

}
