package ru.practicum.mapper.sensor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.mapper.TimestampMapper;
import ru.practicum.model.sensor.TemperatureSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;

@Mapper(uses = TimestampMapper.class)
public interface TemperatureSensorEventMapper {
    TemperatureSensorEventMapper INSTANCE = Mappers.getMapper(TemperatureSensorEventMapper.class);

    TemperatureSensorAvro toAvro(TemperatureSensorEvent temperatureSensorEvent);

    @Mapping(target = "temperatureC", source = "sensorRequest.temperatureSensorEvent.temperatureC")
    @Mapping(target = "temperatureF", source = "sensorRequest.temperatureSensorEvent.temperatureF")
    TemperatureSensorEvent toSensorEvent(SensorEventProto sensorRequest);
}