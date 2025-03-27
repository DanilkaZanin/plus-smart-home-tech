package ru.practicum.mapper.sensor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.mapper.TimestampMapper;
import ru.practicum.model.sensor.MotionSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;

@Mapper(uses = TimestampMapper.class)
public interface MotionSensorEventMapper {
    MotionSensorEventMapper INSTANCE = Mappers.getMapper(MotionSensorEventMapper.class);

    MotionSensorAvro toAvro(MotionSensorEvent motionSensorEvent);

    @Mapping(target = "linkQuality", source = "sensorRequest.motionSensorEvent.linkQuality")
    @Mapping(target = "motion", source = "sensorRequest.motionSensorEvent.motion")
    @Mapping(target = "voltage", source = "sensorRequest.motionSensorEvent.voltage")
    MotionSensorEvent toSensorEvent(SensorEventProto sensorRequest);
}