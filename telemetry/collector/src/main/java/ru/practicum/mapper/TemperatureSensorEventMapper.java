package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.sensor.TemperatureSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;

@Mapper
public interface TemperatureSensorEventMapper {
    TemperatureSensorEventMapper INSTANCE = Mappers.getMapper(TemperatureSensorEventMapper.class);

    TemperatureSensorAvro toAvro(TemperatureSensorEvent temperatureSensorEvent);
}