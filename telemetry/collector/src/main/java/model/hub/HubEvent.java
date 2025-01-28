package model.hub;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import model.hub.type.DeviceType;
import model.hub.type.HubEventType;

import java.sql.Timestamp;

@Getter
@Setter
public class HubEvent {
    @NotBlank
    private String hubId;
    @NotBlank
    private Timestamp timestamp;
    private HubEventType type;
    @NotBlank
    private String id;
    private DeviceType deviceType;
}