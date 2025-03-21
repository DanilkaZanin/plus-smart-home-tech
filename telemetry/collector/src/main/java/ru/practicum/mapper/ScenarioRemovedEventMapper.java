package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Mapper(uses = TimestampMapper.class)
public interface ScenarioRemovedEventMapper {
    ScenarioRemovedEventMapper INSTANCE = Mappers.getMapper(ScenarioRemovedEventMapper.class);

    ScenarioRemovedEventAvro toAvro(ScenarioRemovedEvent event);

    ScenarioRemovedEvent toHubEvent(HubEventProto HubRequest);
}