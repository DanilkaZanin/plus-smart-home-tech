package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.hub.DeviceRemovedEvent;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;

@Mapper(uses = TimestampMapper.class)
public interface DeviceRemovedEventMapper {
    DeviceRemovedEventMapper INSTANCE = Mappers.getMapper(DeviceRemovedEventMapper.class);

    DeviceRemovedEventAvro toAvro(DeviceRemovedEvent deviceRemovedEvent);

    DeviceRemovedEvent toHubEvent(HubEventProto HubRequest);
}