package ru.practicum.mapper;

import ru.practicum.model.hub.HubEvent;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public interface HubEventAvroMapper {
    HubEventAvro map(HubEvent event);
}
