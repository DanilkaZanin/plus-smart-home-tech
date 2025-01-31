package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.sensor.MotionSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;

@Mapper
public interface MotionSensorEventMapper {
    MotionSensorEventMapper INSTANCE = Mappers.getMapper(MotionSensorEventMapper.class);

    MotionSensorAvro toAvro(MotionSensorEvent motionSensorEvent);
}