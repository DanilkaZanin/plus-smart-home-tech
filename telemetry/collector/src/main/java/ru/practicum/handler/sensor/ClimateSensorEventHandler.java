package ru.practicum.handler.sensor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.mapper.sensor.ClimateSensorEventMapper;
import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

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
        return ClimateSensorEventMapper.INSTANCE.toSensorEvent(event);
    }
}
