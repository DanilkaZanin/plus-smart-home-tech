package ru.practicum.mapper.device;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.mapper.TimestampMapper;
import ru.practicum.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;

@Mapper(uses = TimestampMapper.class)
public interface DeviceAddedEventMapper {
    DeviceAddedEventMapper INSTANCE = Mappers.getMapper(DeviceAddedEventMapper.class);

    @Mapping(target = "type", source = "deviceType")
    DeviceAddedEventAvro toAvro(DeviceAddedEvent deviceAddedEvent);
}