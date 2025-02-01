package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;

@Mapper
public interface ScenarioAddedEventMapper {
    ScenarioAddedEventMapper INSTANCE = Mappers.getMapper(ScenarioAddedEventMapper.class);

    ScenarioAddedEventAvro toAvro(ScenarioAddedEvent scenarioAddedEvent);
}
