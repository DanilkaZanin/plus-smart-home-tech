package ru.practicum.mapper;

import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

public interface SensorEventAvroMapper {
    SensorEventAvro map(SensorEvent sensorEvent);
}
