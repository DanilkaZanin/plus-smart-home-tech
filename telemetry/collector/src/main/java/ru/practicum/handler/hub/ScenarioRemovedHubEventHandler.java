package ru.practicum.handler.hub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.model.hub.HubEvent;
import ru.practicum.model.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

import java.time.Instant;

@Slf4j
@Component
public class ScenarioRemovedHubEventHandler implements HubEventHandler {
    @Override
    public HubEventProto.PayloadCase getMessageType() {
        log.info("ScenarioRemovedEventHandler отдал тип сообщения SCENARIO_REMOVED");
        return HubEventProto.PayloadCase.SCENARIO_REMOVED;
    }

    @Override
    public HubEvent handle(HubEventProto event) {
        log.info("ScenarioRemovedEventHandler начал обработку ивента: {} ", event.getHubId());
        return ScenarioRemovedEvent.builder()
                .hubId(event.getHubId())
                .timestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .name(event.getScenarioRemoved().getName())
                .build();
    }
}
