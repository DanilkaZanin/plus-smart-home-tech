package ru.practicum.handler.sensor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.SensorEvent;
import ru.practicum.model.sensor.SwitchSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

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
        return SwitchSensorEvent.builder()
                .id(event.getId())
                .hubId(event.getHubId())
                .timestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .state(event.getSwitchSensorEvent().getState())
                .build();
    }
}
