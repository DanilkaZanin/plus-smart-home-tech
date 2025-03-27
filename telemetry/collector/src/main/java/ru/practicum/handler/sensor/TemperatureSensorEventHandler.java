package ru.practicum.handler.sensor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.mapper.sensor.TemperatureSensorEventMapper;
import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

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
        return TemperatureSensorEventMapper.INSTANCE.toSensorEvent(event);
    }
}
