package ru.practicum.handler.sensor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.mapper.sensor.SwitchSensorEventMapper;
import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

@Slf4j
@Component
public class SwitchSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        log.info("SwitchSensorEventHandler отдал тип сообщения SWITCH_SENSOR_EVENT");
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }

    @Override
    public SensorEvent handle(SensorEventProto event) {
        log.info("SwitchSensorEventHandler начал обработку ивента: {}", event.getId());
        return SwitchSensorEventMapper.INSTANCE.toSensorEvent(event);
    }
}
