package ru.practicum.handler.hub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.mapper.scenario.ScenarioAddedEventMapper;
import ru.practicum.model.hub.HubEvent;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

@Slf4j
@Component
public class ScenarioAddedHubEventHandler implements HubEventHandler {
    @Override
    public HubEventProto.PayloadCase getMessageType() {
        log.info("ScenarioAddedEventHandler отдал тип сообщения SCENARIO_ADDED");
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    public HubEvent handle(HubEventProto event) {
        log.info("ScenarioAddedEventHandler начал обработку ивента: {} ", event.getHubId());
        return ScenarioAddedEventMapper.INSTANCE.toHubEvent(event);
    }
}
