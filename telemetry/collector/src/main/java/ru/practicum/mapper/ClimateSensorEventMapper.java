package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.sensor.ClimateSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;

@Mapper(uses = TimestampMapper.class)
public interface ClimateSensorEventMapper {
    ClimateSensorEventMapper INSTANCE = Mappers.getMapper(ClimateSensorEventMapper.class);

    @Mapping(source = "temperature", target = "temperatureC")
    ClimateSensorAvro toAvro(ClimateSensorEvent climateSensorEvent);

    ClimateSensorEvent toSensorEvent(SensorEventProto sensorRequest);
}