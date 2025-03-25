package ru.program.aggregator.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Getter
@Setter
@Configuration
public class KafkaConfig {
    @Value("${kafka.producer.bootstrap-server}")
    private String bootstrapService;
    @Value("${kafka.producer.key-serializer}")
    private String producerKeySerializer;
    @Value("${kafka.producer.value-serializer}")
    private String producerValueSerializer;
    @Value("${kafka.consumer.group-id}")
    private String consumerGroupId;
    @Value("${kafka.consumer.client-id-config}")
    private String consumerClientIdConfig;
    @Value("${kafka.consumer.key-deserializer}")
    private String consumerKeyDeserializer;
    @Value("${kafka.consumer.value-deserializer}")
    private String consumerValueDeserializer;

    @Bean
    public KafkaConsumer<String, SpecificRecordBase> getConsumer() {
        Properties config = new Properties();
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, consumerClientIdConfig);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapService);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerKeyDeserializer);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerValueDeserializer);
        return new KafkaConsumer<>(config);
    }

    @Bean
    public Producer<String, SpecificRecordBase> getProducer() {
        Properties config = new Properties();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapService);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerKeySerializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerValueSerializer);

        return new KafkaProducer<>(config);
    }
}
