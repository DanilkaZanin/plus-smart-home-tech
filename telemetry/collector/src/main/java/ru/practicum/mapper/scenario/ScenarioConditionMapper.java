package ru.practicum.mapper.scenario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mapper.UnrecognizedTypeConverter;
import ru.practicum.model.hub.ScenarioCondition;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;

@Mapper(uses = UnrecognizedTypeConverter.class)
public interface ScenarioConditionMapper {

    @Mapping(target = "sensorId", source = "sensorId")
    @Mapping(target = "type", source = "type", qualifiedByName = "scenarioConditionType")
    @Mapping(target = "operation", source = "operation", qualifiedByName = "operation")
    @Mapping(target = "value", expression = "java(proto.hasBoolValue() ?  (proto.getBoolValue() ? 1 : 0) : (proto.hasIntValue() ? proto.getIntValue() : null))")
    ScenarioCondition map(ScenarioConditionProto proto);
}
