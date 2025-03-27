package ru.practicum.mapper.scenario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mapper.UnrecognizedTypeConverter;
import ru.practicum.model.hub.DeviceAction;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;

@Mapper(uses = UnrecognizedTypeConverter.class)
public interface DeviceActionMapper {

    @Mapping(target = "sensorId", source = "sensorId")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "type", source = "type", qualifiedByName = "deviceActionType")
    DeviceAction map(DeviceActionProto proto);
}
