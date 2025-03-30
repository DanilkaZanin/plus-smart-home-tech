package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.HubEventAvroMapper;
import ru.practicum.mapper.SensorEventAvroMapper;
import ru.practicum.model.hub.HubEvent;
import ru.practicum.model.sensor.SensorEvent;

@Slf4j
@Service
@RequiredArgsConstructor()
public class KafkaServiceImpl implements KafkaService {
    private final KafkaProducer<Void, SpecificRecordBase> kafkaProducer;
    private final HubEventAvroMapper hubEventAvroMapper;
    private final SensorEventAvroMapper sensorEventAvroMapper;

    @Value("${spring.kafka.producer.topic.hubs}")
    private String hubTopic;

    @Value("${spring.kafka.producer.topic.sensors}")
    private String sensorTopic;

    @Override
    public void sendSensorToKafka(SensorEvent event) {
        sendToKafka(sensorTopic, sensorEventAvroMapper.map(event));
    }

    @Override
    public void sendHubToKafka(HubEvent event) {

        sendToKafka(hubTopic, hubEventAvroMapper.map(event));
    }

    private void sendToKafka(String topic,SpecificRecordBase specificRecordBase) {
        kafkaProducer.send(new ProducerRecord<>(topic, specificRecordBase), (metadata, exception) -> {
            if (exception != null) {
                log.error("Ошибка при отправке сообщения в Kafka", exception);
            } else {
                log.info("Сообщение отправлено! Topic:{} Offset: {}", metadata.topic(), metadata.offset());
            }
        });
    }
}