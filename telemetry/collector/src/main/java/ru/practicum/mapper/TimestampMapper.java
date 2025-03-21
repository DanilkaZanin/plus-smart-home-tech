package ru.practicum.mapper;

import com.google.protobuf.Timestamp;
import java.time.Instant;

public class TimestampMapper {

    public Instant toInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }

    public Timestamp toTimestamp(Instant instant) {
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    public Timestamp map(Timestamp value) {
        return value;
    }
}
