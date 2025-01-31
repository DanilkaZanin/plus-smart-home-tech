package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.hub.DeviceRemovedEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;

@Mapper
public interface DeviceRemovedEventMapper {
    DeviceRemovedEventMapper INSTANCE = Mappers.getMapper(DeviceRemovedEventMapper.class);

    DeviceRemovedEventAvro toAvro(DeviceRemovedEvent deviceRemovedEvent);
}