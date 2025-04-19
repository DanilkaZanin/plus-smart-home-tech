package ru.practicum.model.sensor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.practicum.model.sensor.type.SensorEventType;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class LightSensorEvent extends SensorEvent{
    private int linkQuality;
    private int luminosity;

    @Override
    public SensorEventType getType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }
}