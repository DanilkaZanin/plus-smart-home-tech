package ru.practicum.mapper;

import org.mapstruct.Named;
import ru.practicum.model.hub.type.DeviceType;
import ru.practicum.model.hub.type.DeviseActionType;
import ru.practicum.model.hub.type.Operation;
import ru.practicum.model.hub.type.ScenarioConditionType;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.ConditionOperationProto;
import ru.yandex.practicum.grpc.telemetry.event.ConditionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceTypeProto;

public class UnrecognizedTypeConverter {

    @Named("deviceType")
    public DeviceType convert(DeviceTypeProto proto) {
        if (proto == DeviceTypeProto.UNRECOGNIZED) {
            throw new IllegalArgumentException("Unrecognized device type: " + proto);
        }
        return DeviceType.valueOf(proto.name());
    }

    @Named("scenarioConditionType")
    public ScenarioConditionType convert(ConditionTypeProto proto) {
        if (proto == ConditionTypeProto.UNRECOGNIZED) {
            throw new IllegalArgumentException("Unrecognized condition type: " + proto);
        }
        return ScenarioConditionType.valueOf(proto.name());
    }

    @Named("operation")
    public Operation convert(ConditionOperationProto proto) {
        if (proto == ConditionOperationProto.UNRECOGNIZED) {
            throw new IllegalArgumentException("Unrecognized condition operation type: " + proto);
        }
        return Operation.valueOf(proto.name());
    }

    @Named("deviceActionType")
    public DeviseActionType convert(ActionTypeProto proto) {
        if (proto == ActionTypeProto.UNRECOGNIZED) {
            throw new IllegalArgumentException("Unrecognized action type: " + proto);
        }
        return DeviseActionType.valueOf(proto.name());
    }
}
