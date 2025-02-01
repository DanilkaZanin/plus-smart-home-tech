package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.sensor.LightSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;

@Mapper
public interface LightSensorEventMapper {
    LightSensorEventMapper INSTANCE = Mappers.getMapper(LightSensorEventMapper.class);

    LightSensorAvro toAvro(LightSensorEvent lightSensorEvent);
}
