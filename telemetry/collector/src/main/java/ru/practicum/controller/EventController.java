package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.hub.HubEvent;
import ru.practicum.model.sensor.SensorEvent;
import ru.practicum.service.KafkaService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor()
public class EventController {
    private final KafkaService kafkaService;

    @Value("${spring.kafka.producer.topic.hubs}")
    private String hubPath;

    @Value("${spring.kafka.producer.topic.sensors}")
    private String sensorPath;

    @PostMapping("/sensors")
    @ResponseStatus(HttpStatus.OK)
    public void collectSensorEvent(@Valid @RequestBody SensorEvent event) {
        kafkaService.sendToKafka(sensorPath, event);
    }

    @PostMapping("/hubs")
    @ResponseStatus(HttpStatus.OK)
    public void collectHubEvent(@Valid @RequestBody HubEvent event) {
        kafkaService.sendToKafka(hubPath, event);
    }
}