package ru.practicum.mapper.sensor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.mapper.TimestampMapper;
import ru.practicum.model.sensor.MotionSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;

@Mapper(uses = TimestampMapper.class)
public interface MotionSensorEventMapper {
    MotionSensorEventMapper INSTANCE = Mappers.getMapper(MotionSensorEventMapper.class);

    MotionSensorAvro toAvro(MotionSensorEvent motionSensorEvent);
}