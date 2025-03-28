package ru.practicum.model.hub;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.model.hub.type.DeviceActionType;

@Getter
@Setter
@ToString
@Builder
public class DeviceAction {
    private String sensorId;
    private DeviceActionType type;
    private String value;
}