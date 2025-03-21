package ru.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.mapper.ClimateSensorEventMapper;
import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

@Component
public class ClimateSensorEventHandler implements SensorEventHandler {
    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT;
    }

    @Override
    public SensorEvent handle(SensorEventProto event) {
        return ClimateSensorEventMapper.INSTANCE.toSensorEvent(event);
    }
}
