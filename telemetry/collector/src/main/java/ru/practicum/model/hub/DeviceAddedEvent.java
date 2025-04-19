package ru.practicum.model.hub;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.practicum.model.hub.type.DeviceType;
import ru.practicum.model.hub.type.HubEventType;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class DeviceAddedEvent extends HubEvent {
    @NotBlank
    private String id;

    private DeviceType deviceType;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}