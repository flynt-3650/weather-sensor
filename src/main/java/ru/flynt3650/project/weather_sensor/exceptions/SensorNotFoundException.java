package ru.flynt3650.project.weather_sensor.exceptions;

public class SensorNotFoundException extends RuntimeException {
    public SensorNotFoundException(String message) {
        super(message);
    }
}
