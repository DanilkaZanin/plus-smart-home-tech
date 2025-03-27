package ru.practicum.mapper.scenario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.mapper.TimestampMapper;
import ru.practicum.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;

@Mapper(uses = {TimestampMapper.class , ScenarioConditionMapper.class, DeviceActionMapper.class})
public interface ScenarioAddedEventMapper {
    ScenarioAddedEventMapper INSTANCE = Mappers.getMapper(ScenarioAddedEventMapper.class);

    ScenarioAddedEventAvro toAvro(ScenarioAddedEvent scenarioAddedEvent);

    @Mapping(target = "name", source = "hubRequest.scenarioAdded.name")
    @Mapping(target = "conditions", source = "hubRequest.scenarioAdded.conditionList", defaultExpression = "java(new ArrayList<>())")
    @Mapping(target = "actions", source = "hubRequest.scenarioAdded.actionList",  defaultExpression = "java(new ArrayList<>())")
    ScenarioAddedEvent toHubEvent(HubEventProto hubRequest);
}
