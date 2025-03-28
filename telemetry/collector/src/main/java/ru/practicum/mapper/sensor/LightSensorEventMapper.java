package ru.practicum.mapper.sensor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.mapper.TimestampMapper;
import ru.practicum.model.sensor.LightSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;

@Mapper(uses = TimestampMapper.class)
public interface LightSensorEventMapper {
    LightSensorEventMapper INSTANCE = Mappers.getMapper(LightSensorEventMapper.class);

    LightSensorAvro toAvro(LightSensorEvent lightSensorEvent);
}
