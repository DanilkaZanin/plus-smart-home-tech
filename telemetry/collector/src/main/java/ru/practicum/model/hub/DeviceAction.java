package ru.practicum.model.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.model.hub.type.DeviseActionType;

@Getter
@Setter
@ToString
public class DeviceAction {
    private String sensorId;
    private DeviseActionType type;
    private String value;
}