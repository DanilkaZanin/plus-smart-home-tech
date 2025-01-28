package controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import model.hub.HubEvent;
import model.sensor.SensorEvent;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {

    @PostMapping("/sensors")
    @ResponseStatus(HttpStatus.OK)
    public void collectSensorEvent(@Valid @RequestBody SensorEvent event) {
        // вроде реализация пока не нужна
    }

    @PostMapping("/hubs")
    @ResponseStatus(HttpStatus.OK)
    public void collectHubEvent(@Valid @RequestBody HubEvent event) {
        // вроде реализация пока не нужна
    }
}