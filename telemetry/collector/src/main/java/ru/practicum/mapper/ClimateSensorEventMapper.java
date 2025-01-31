package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.sensor.ClimateSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;

@Mapper
public interface ClimateSensorEventMapper {
    ClimateSensorEventMapper INSTANCE = Mappers.getMapper(ClimateSensorEventMapper.class);

    ClimateSensorAvro toAvro(ClimateSensorEvent climateSensorEvent);
}