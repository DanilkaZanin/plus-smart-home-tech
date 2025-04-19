package ru.practicum.handler.hub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.model.hub.DeviceRemovedEvent;
import ru.practicum.model.hub.HubEvent;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

import java.time.Instant;

@Slf4j
@Component
public class DeviceRemovedHubEventHandler implements HubEventHandler {
    @Override
    public HubEventProto.PayloadCase getMessageType() {
        log.info("DeviceRemovedEventHandler отдал тип сообщения DEVICE_REMOVED");
        return HubEventProto.PayloadCase.DEVICE_REMOVED;
    }

    @Override
    public HubEvent handle(HubEventProto event) {
        log.info("DeviceRemovedEventHandler начал обработку ивента: {} ", event.getHubId());
        return DeviceRemovedEvent.builder()
                .hubId(event.getHubId())
                .timestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .id(event.getDeviceRemoved().getId())
                .build();
    }
}
