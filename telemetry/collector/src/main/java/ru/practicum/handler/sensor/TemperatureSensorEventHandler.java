package ru.practicum.handler.sensor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.SensorEvent;
import ru.practicum.model.sensor.TemperatureSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

@Slf4j
@Component
public class TemperatureSensorEventHandler implements SensorEventHandler {
    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        log.info("TemperatureSensorEventHandler отдал тип сообщения TEMPERATURE_SENSOR_EVENT");
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public SensorEvent handle(SensorEventProto event) {
        log.info("TemperatureSensorEventHandler начал обработку ивента: {}", event.getId());
        return TemperatureSensorEvent.builder()
                .id(event.getId())
                .hubId(event.getHubId())
                .timestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .temperatureC(event.getTemperatureSensorEvent().getTemperatureC())
                .temperatureF(event.getTemperatureSensorEvent().getTemperatureF())
                .build();
    }
}
