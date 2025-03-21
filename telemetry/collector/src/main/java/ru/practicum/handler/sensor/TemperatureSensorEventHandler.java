package ru.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.mapper.TemperatureSensorEventMapper;
import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

@Component
public class TemperatureSensorEventHandler implements SensorEventHandler {
    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public SensorEvent handle(SensorEventProto event) {
        return TemperatureSensorEventMapper.INSTANCE.toSensorEvent(event);
    }
}
