package ru.practicum.model.sensor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.model.sensor.type.SensorEventType;

@Getter
@Setter
@ToString(callSuper = true)
public class SwitchSensorEvent extends SensorEvent{
    private boolean state;


    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}