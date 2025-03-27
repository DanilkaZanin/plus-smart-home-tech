package ru.practicum.handler.sensor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.mapper.sensor.MotionSensorEventMapper;
import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

@Slf4j
@Component
public class MotionSensorEventHandler implements SensorEventHandler {
    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        log.info("MotionSensorEvent Handler отдал тип сообщения MOTION_SENSOR_EVENT");
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }

    @Override
    public SensorEvent handle(SensorEventProto event) {
        log.info("MotionSensorEventHandler начал обработку ивента: {}", event.getId());
        return MotionSensorEventMapper.INSTANCE.toSensorEvent(event);
    }
}
