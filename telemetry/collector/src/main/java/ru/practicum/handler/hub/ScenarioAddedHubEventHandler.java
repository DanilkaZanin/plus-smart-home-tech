package ru.practicum.handler.hub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.model.hub.DeviceAction;
import ru.practicum.model.hub.HubEvent;
import ru.practicum.model.hub.ScenarioAddedEvent;
import ru.practicum.model.hub.ScenarioCondition;
import ru.practicum.model.hub.type.DeviceActionType;
import ru.practicum.model.hub.type.Operation;
import ru.practicum.model.hub.type.ScenarioConditionType;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;

import java.time.Instant;
import java.util.List;

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
        return ScenarioAddedEvent.builder()
                .hubId(event.getHubId())
                .timestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .name(event.getScenarioAdded().getName())
                .conditions(scenarioConditions(event.getScenarioAdded().getConditionList()))
                .actions(deviceActions(event.getScenarioAdded().getActionList()))
                .build();
    }


    private List<ScenarioCondition> scenarioConditions(List<ScenarioConditionProto> protos) {
        return protos.stream()
                .map(proto -> ScenarioCondition.builder()
                        .sensorId(proto.getSensorId())
                        .type(ScenarioConditionType.valueOf(proto.getType().toString()))
                        .operation(Operation.valueOf(proto.getOperation().toString()))
                        .value(proto.hasBoolValue() ? (proto.getBoolValue() ? 1 : 0) : proto.getIntValue())
                        .build())
                .toList();
    }

    private List<DeviceAction> deviceActions(List<DeviceActionProto> protos) {
        return protos.stream()
                .map(proto -> DeviceAction.builder()
                        .sensorId(proto.getSensorId())
                        .type(DeviceActionType.valueOf(proto.getType().toString()))
                        .value(String.valueOf(proto.getValue()))
                        .build())
                .toList();
    }
}
