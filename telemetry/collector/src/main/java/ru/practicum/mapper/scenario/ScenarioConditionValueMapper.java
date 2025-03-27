package ru.practicum.mapper.scenario;

import org.mapstruct.Named;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;

public class ScenarioConditionValueMapper {
    @Named("mapValue")
    public Object mapValue(ScenarioConditionProto proto) {
        if (proto.hasBoolValue()) return proto.getBoolValue();
        if (proto.hasIntValue()) return proto.getIntValue();
        return null;
    }
}
