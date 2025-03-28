package ru.practicum.model.hub;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.model.hub.type.Operation;
import ru.practicum.model.hub.type.ScenarioConditionType;

@Getter
@Setter
@ToString
@Builder
public class ScenarioCondition {
    @NotBlank
    private String sensorId;
    private ScenarioConditionType type;
    private Operation operation;
    private int value;
}