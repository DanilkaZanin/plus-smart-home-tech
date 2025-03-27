package ru.practicum.handler.hub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.mapper.device.DeviceRemovedEventMapper;
import ru.practicum.model.hub.HubEvent;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

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
        return DeviceRemovedEventMapper.INSTANCE.toHubEvent(event);
    }
}
