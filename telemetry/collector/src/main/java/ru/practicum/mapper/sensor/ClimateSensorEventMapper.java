package ru.practicum.mapper.sensor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.mapper.TimestampMapper;
import ru.practicum.model.sensor.ClimateSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;

@Mapper(uses = TimestampMapper.class)
public interface ClimateSensorEventMapper {
    ClimateSensorEventMapper INSTANCE = Mappers.getMapper(ClimateSensorEventMapper.class);

    @Mapping(target = "temperatureC", source = "temperature")
    ClimateSensorAvro toAvro(ClimateSensorEvent climateSensorEvent);

    @Mapping(target = "temperature", source = "sensorRequest.climateSensorEvent.temperatureC")
    @Mapping(target = "humidity",source = "sensorRequest.climateSensorEvent.humidity")
    @Mapping(target = "co2Level", source = "sensorRequest.climateSensorEvent.co2Level")
    ClimateSensorEvent toSensorEvent(SensorEventProto sensorRequest);
}