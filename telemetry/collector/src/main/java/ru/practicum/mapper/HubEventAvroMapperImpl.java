package ru.practicum.mapper;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;
import ru.practicum.mapper.device.DeviceAddedEventMapper;
import ru.practicum.mapper.device.DeviceRemovedEventMapper;
import ru.practicum.mapper.scenario.ScenarioAddedEventMapper;
import ru.practicum.mapper.scenario.ScenarioRemovedEventMapper;
import ru.practicum.model.hub.*;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.function.Function;

@Component
public class HubEventAvroMapperImpl implements HubEventAvroMapper {

    @Override
    public HubEventAvro map(HubEvent event) {
        return switch (event.getType()) {
            case DEVICE_ADDED -> map((DeviceAddedEvent) event);
            case DEVICE_REMOVED -> map((DeviceRemovedEvent) event);
            case SCENARIO_ADDED -> map((ScenarioAddedEvent) event);
            case SCENARIO_REMOVED -> map((ScenarioRemovedEvent) event);
        };
    }

    private HubEventAvro map(DeviceAddedEvent event) {
        return mapGeneric(event, DeviceAddedEventMapper.INSTANCE::toAvro);
    }

    private HubEventAvro map(DeviceRemovedEvent event) {
        return mapGeneric(event, DeviceRemovedEventMapper.INSTANCE::toAvro);
    }

    private HubEventAvro map(ScenarioAddedEvent event) {
        return mapGeneric(event, ScenarioAddedEventMapper.INSTANCE::toAvro);
    }

    private HubEventAvro map(ScenarioRemovedEvent event) {
        return mapGeneric(event, ScenarioRemovedEventMapper.INSTANCE::toAvro);
    }

    private <T extends HubEvent, R extends SpecificRecord> HubEventAvro mapGeneric(T event,
                                                                                   Function<T, R> payloadMapper) {
        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payloadMapper.apply(event))
                .build();
    }
}
