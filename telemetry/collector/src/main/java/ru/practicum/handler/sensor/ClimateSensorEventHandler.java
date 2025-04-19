package ru.practicum.handler.sensor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.ClimateSensorEvent;
import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

@Slf4j
@Component
public class ClimateSensorEventHandler implements SensorEventHandler {
    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        log.info("ClimateSensorEventHandler отдал тип сообщения CLIMATE_SENSOR_EVENT");
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT;
    }

    @Override
    public SensorEvent handle(SensorEventProto event) {
        log.info("ClimateSensorEventHandler начал обработку ивента: {} ", event.getId());
        return ClimateSensorEvent.builder()
                .id(event.getId())
                .hubId(event.getHubId())
                .timestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .temperature(event.getClimateSensorEvent().getTemperatureC())
                .humidity(event.getClimateSensorEvent().getHumidity())
                .co2Level(event.getClimateSensorEvent().getCo2Level())
                .build();
    }
}
