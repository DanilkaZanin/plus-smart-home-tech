package ru.practicum.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.handler.hub.HubEventHandler;
import ru.practicum.model.hub.HubEvent;
import ru.practicum.model.sensor.SensorEvent;
import ru.practicum.handler.sensor.SensorEventHandler;
import ru.practicum.service.KafkaService;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@GrpcService
public class EventController extends CollectorControllerGrpc.CollectorControllerImplBase {
    private final KafkaService kafkaService;
    private final Map<SensorEventProto.PayloadCase, SensorEventHandler> sensorEventHandlers;
    private final Map<HubEventProto.PayloadCase, HubEventHandler> hubEventHandlers;

    public EventController(KafkaService kafkaService,
                           Set<SensorEventHandler> sensorEventHandlers,
                           Set<HubEventHandler> hubEventHandlers) {
        this.kafkaService = kafkaService;

        this.sensorEventHandlers = sensorEventHandlers.stream()
                .collect(Collectors.toMap(
                        SensorEventHandler::getMessageType,
                        Function.identity()
                ));

        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(
                        HubEventHandler::getMessageType,
                        Function.identity()
                ));
    }

    @Value("${spring.kafka.producer.topic.hubs}")
    private String hubPath;

    @Value("${spring.kafka.producer.topic.sensors}")
    private String sensorPath;


    @Override
    public void collectSensorEvent(SensorEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            if (sensorEventHandlers.containsKey(request.getPayloadCase())) {
                SensorEvent event = sensorEventHandlers.get(request.getPayloadCase()).handle(request);
                collectSensorEvent(event);
            } else {
                throw new IllegalArgumentException("Не могу найти обработчик для события " + request.getPayloadCase());
            }
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL
                            .withDescription(e.getLocalizedMessage())
                            .withCause(e)
            ));
        }
    }

    @Override
    public void collectHubEvent(HubEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            if (hubEventHandlers.containsKey(request.getPayloadCase())) {
                HubEvent event = hubEventHandlers.get(request.getPayloadCase()).handle(request);
                collectHubEvent(event);
            } else {
                throw new IllegalArgumentException("Не могу найти обработчик для хаба " + request.getPayloadCase());
            }
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL
                            .withDescription(e.getLocalizedMessage())
                            .withCause(e)
            ));
        }
    }

    public void collectSensorEvent(@Valid SensorEvent event) {
        kafkaService.sendToKafka(sensorPath, event);
    }

    public void collectHubEvent(@Valid HubEvent event) {
        kafkaService.sendToKafka(hubPath, event);
    }
}