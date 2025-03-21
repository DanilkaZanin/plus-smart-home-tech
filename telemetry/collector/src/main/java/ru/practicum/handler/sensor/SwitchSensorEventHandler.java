package ru.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.mapper.SwitchSensorEventMapper;
import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

@Component
public class SwitchSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }

    @Override
    public SensorEvent handle(SensorEventProto event) {
        return SwitchSensorEventMapper.INSTANCE.toSensorEvent(event);
    }
}
