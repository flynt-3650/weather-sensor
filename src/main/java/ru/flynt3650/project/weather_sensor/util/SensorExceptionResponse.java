package ru.flynt3650.project.weather_sensor.util;

import ru.flynt3650.project.weather_sensor.models.Sensor;

public class SensorExceptionResponse {
    private String message;
    private long timestamp;

    public SensorExceptionResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
