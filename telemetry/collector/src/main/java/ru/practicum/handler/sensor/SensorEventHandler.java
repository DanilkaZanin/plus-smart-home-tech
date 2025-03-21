package ru.practicum.handler.sensor;

import ru.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;


public interface SensorEventHandler {
    SensorEventProto.PayloadCase getMessageType();

    SensorEvent handle(SensorEventProto event);
}
