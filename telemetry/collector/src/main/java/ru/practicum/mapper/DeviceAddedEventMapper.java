package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;

@Mapper
public interface DeviceAddedEventMapper {
    DeviceAddedEventMapper INSTANCE = Mappers.getMapper(DeviceAddedEventMapper.class);

    @Mapping(source = "deviceType", target = "type")
    DeviceAddedEventAvro toAvro(DeviceAddedEvent deviceAddedEvent);
}