package ru.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.mapper.LightSensorEventMapper;
import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

@Component
public class LightSensorEventHandler implements SensorEventHandler {
    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }

    @Override
    public SensorEvent handle(SensorEventProto event) {
        return LightSensorEventMapper.INSTANCE.toSensorEvent(event);
    }
}
