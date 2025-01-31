package ru.practicum.mapper;

import org.mapstruct.factory.Mappers;
import ru.practicum.model.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

public interface ScenarioRemovedEventMapper {
    ScenarioRemovedEventMapper INSTANCE = Mappers.getMapper(ScenarioRemovedEventMapper.class);

    ScenarioRemovedEventAvro toAvro(ScenarioRemovedEvent event);
}