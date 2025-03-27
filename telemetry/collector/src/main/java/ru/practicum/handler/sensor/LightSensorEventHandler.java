package ru.practicum.handler.sensor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.mapper.sensor.LightSensorEventMapper;
import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

@Slf4j
@Component
public class LightSensorEventHandler implements SensorEventHandler {
    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        log.info("LightSensorEventHandler отдал тип сообщения LIGHT_SENSOR_EVENT");
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }

    @Override
    public SensorEvent handle(SensorEventProto event) {
        log.info("LightSensorEventHandler начал обработку ивента: {}", event.getId());
        return LightSensorEventMapper.INSTANCE.toSensorEvent(event);
    }
}
