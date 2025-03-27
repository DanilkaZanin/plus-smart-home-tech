package ru.practicum.mapper.sensor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.mapper.TimestampMapper;
import ru.practicum.model.sensor.SwitchSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

@Mapper(uses = TimestampMapper.class)
public interface SwitchSensorEventMapper {
    SwitchSensorEventMapper INSTANCE = Mappers.getMapper(SwitchSensorEventMapper.class);

    SwitchSensorAvro toAvro(SwitchSensorEvent sensorEvent);

    @Mapping(target = "state", source = "sensorRequest.switchSensorEvent.state")
    SwitchSensorEvent toSensorEvent(SensorEventProto sensorRequest);
}