package ru.flynt3650.project.weather_sensor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementDto {

    private Long id;

    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    private Double temperature;

    @NotNull
    private Boolean isRaining;

    @NotNull
    private SensorDto sensor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Boolean isRaining() {
        return isRaining;
    }

    public void setRaining(Boolean isRaining) {
        this.isRaining = isRaining;
    }

    public SensorDto getSensor() {
        return sensor;
    }

    public void setSensor(SensorDto sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementDto{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", isRaining=" + isRaining +
                ", sensorDto=" + sensor +
                '}';
    }
}
