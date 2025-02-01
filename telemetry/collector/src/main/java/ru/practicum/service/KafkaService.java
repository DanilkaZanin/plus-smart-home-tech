package ru.practicum.service;

public interface KafkaService {
    void sendToKafka(String topic, Object message);
}