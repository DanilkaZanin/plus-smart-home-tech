package ru.practicum.mapper.device;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.mapper.TimestampMapper;
import ru.practicum.mapper.UnrecognizedTypeConverter;
import ru.practicum.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;

@Mapper(uses = {TimestampMapper.class, UnrecognizedTypeConverter.class})
public interface DeviceAddedEventMapper {
    DeviceAddedEventMapper INSTANCE = Mappers.getMapper(DeviceAddedEventMapper.class);

    @Mapping(target = "type", source = "deviceType")
    DeviceAddedEventAvro toAvro(DeviceAddedEvent deviceAddedEvent);

    @Mapping(target = "id", source = "hubRequest.deviceAdded.id")
    @Mapping(target = "deviceType", source = "hubRequest.deviceAdded.type", qualifiedByName = "deviceType")
    DeviceAddedEvent toHubEvent(HubEventProto hubRequest);
}