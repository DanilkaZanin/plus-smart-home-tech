package ru.practicum.mapper.scenario;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.mapper.TimestampMapper;
import ru.practicum.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;

@Mapper(uses = TimestampMapper.class)
public interface ScenarioAddedEventMapper {
    ScenarioAddedEventMapper INSTANCE = Mappers.getMapper(ScenarioAddedEventMapper.class);

    ScenarioAddedEventAvro toAvro(ScenarioAddedEvent scenarioAddedEvent);
}
