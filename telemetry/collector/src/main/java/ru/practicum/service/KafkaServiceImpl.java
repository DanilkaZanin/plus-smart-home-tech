package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.*;
import ru.practicum.model.hub.DeviceAddedEvent;
import ru.practicum.model.hub.DeviceRemovedEvent;
import ru.practicum.model.hub.ScenarioAddedEvent;
import ru.practicum.model.hub.ScenarioRemovedEvent;
import ru.practicum.model.sensor.*;

import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor()
public class KafkaServiceImpl implements KafkaService {
    private final KafkaProducer<Void, SpecificRecordBase> kafkaProducer;

    private static final Map<Class<?>, Function<Object, SpecificRecordBase>> EVENT_MAPPERS = Map.of(
            ClimateSensorEvent.class, event -> ClimateSensorEventMapper.INSTANCE.toAvro((ClimateSensorEvent) event),
            LightSensorEvent.class, event -> LightSensorEventMapper.INSTANCE.toAvro((LightSensorEvent) event),
            MotionSensorEvent.class, event -> MotionSensorEventMapper.INSTANCE.toAvro((MotionSensorEvent) event),
            SwitchSensorEvent.class, event -> SwitchSensorEventMapper.INSTANCE.toAvro((SwitchSensorEvent) event),
            TemperatureSensorEvent.class, event -> TemperatureSensorEventMapper.INSTANCE.toAvro((TemperatureSensorEvent) event),

            DeviceAddedEvent.class, event -> DeviceAddedEventMapper.INSTANCE.toAvro((DeviceAddedEvent) event),
            DeviceRemovedEvent.class, event -> DeviceRemovedEventMapper.INSTANCE.toAvro((DeviceRemovedEvent) event),
            ScenarioAddedEvent.class, event -> ScenarioAddedEventMapper.INSTANCE.toAvro((ScenarioAddedEvent) event),
            ScenarioRemovedEvent.class, event -> ScenarioRemovedEventMapper.INSTANCE.toAvro((ScenarioRemovedEvent) event)
    );

    @Override
    public void sendToKafka(String topic, Object event) {
        Function<Object, SpecificRecordBase> mapper = EVENT_MAPPERS.get(event.getClass());

        if (mapper == null) {
            throw new IllegalArgumentException("Unsupported event type: " + event.getClass().getName());
        }

        SpecificRecordBase specificRecordBase = mapper.apply(event);
        kafkaProducer.send(new ProducerRecord<>(topic, specificRecordBase), (metadata, exception) -> {
            if (exception != null) {
                log.error("Ошибка при отправке сообщения в Kafka", exception);
            } else {
                log.info("Сообщение отправлено! Offset: {}", metadata.offset());
            }
        });
    }
}