package ru.practicum.mapper;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;
import ru.practicum.mapper.sensor.*;
import ru.practicum.model.sensor.*;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.util.function.Function;

@Component
public class SensorEventAvroMapperImpl implements SensorEventAvroMapper {
    @Override
    public SensorEventAvro map(SensorEvent event) {
        return switch (event.getType()) {
            case CLIMATE_SENSOR_EVENT -> map((ClimateSensorEvent) event);
            case LIGHT_SENSOR_EVENT -> map((LightSensorEvent) event);
            case MOTION_SENSOR_EVENT -> map((MotionSensorEvent) event);
            case SWITCH_SENSOR_EVENT -> map((SwitchSensorEvent) event);
            case TEMPERATURE_SENSOR_EVENT -> map((TemperatureSensorEvent) event);
        };
    }

    private SensorEventAvro map(ClimateSensorEvent event) {
        return mapGeneric(event, ClimateSensorEventMapper.INSTANCE::toAvro);
    }


    private SensorEventAvro map(LightSensorEvent event) {
        return mapGeneric(event, LightSensorEventMapper.INSTANCE::toAvro);
    }


    private SensorEventAvro map(MotionSensorEvent event) {
        return mapGeneric(event, MotionSensorEventMapper.INSTANCE::toAvro);
    }


    private SensorEventAvro map(SwitchSensorEvent event) {
        return mapGeneric(event, SwitchSensorEventMapper.INSTANCE::toAvro);
    }


    private SensorEventAvro map(TemperatureSensorEvent event) {
        return mapGeneric(event, TemperatureSensorEventMapper.INSTANCE::toAvro);
    }

    private <T extends SensorEvent, R extends SpecificRecord> SensorEventAvro mapGeneric(T event,
                                                                                   Function<T, R> payloadMapper) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payloadMapper.apply(event))
                .build();
    }
}
