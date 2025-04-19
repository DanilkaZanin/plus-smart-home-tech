package ru.practicum.handler.hub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.model.hub.DeviceAddedEvent;
import ru.practicum.model.hub.HubEvent;
import ru.practicum.model.hub.type.DeviceType;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

import java.time.Instant;

@Slf4j
@Component
public class DeviceAddedHubEventHandler implements HubEventHandler {
    @Override
    public HubEventProto.PayloadCase getMessageType() {
        log.info("DeviceAddedEventHandler отдал тип сообщения DEVICE_ADDED");
        return HubEventProto.PayloadCase.DEVICE_ADDED;
    }

    @Override
    public HubEvent handle(HubEventProto event) {
        log.info("DeviceAddedEventHandler начал обработку ивента: {} ", event.getHubId());
        return DeviceAddedEvent.builder()
                .hubId(event.getHubId())
                .timestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .id(event.getDeviceAdded().getId())
                .deviceType(DeviceType.valueOf(event.getDeviceAdded().getType().toString()))
                .build();
    }
}
