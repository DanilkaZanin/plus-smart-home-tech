package ru.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.mapper.MotionSensorEventMapper;
import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

@Component
public class MotionSensorEventHandler implements SensorEventHandler {
    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }

    @Override
    public SensorEvent handle(SensorEventProto event) {
        return MotionSensorEventMapper.INSTANCE.toSensorEvent(event);
    }
}
