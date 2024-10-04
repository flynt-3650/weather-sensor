package ru.flynt3650.project.weather_sensor.util;

public class MeasurementExceptionResponse {

    private String message;
    private long timestamp;

    public MeasurementExceptionResponse(String message, long timestamp) {
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
