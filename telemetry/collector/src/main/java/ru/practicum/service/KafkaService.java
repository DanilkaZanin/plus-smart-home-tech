package ru.practicum.service;

import ru.practicum.model.hub.HubEvent;
import ru.practicum.model.sensor.SensorEvent;

public interface KafkaService {
    void sendSensorToKafka(SensorEvent message);
    void sendHubToKafka(HubEvent message);
}