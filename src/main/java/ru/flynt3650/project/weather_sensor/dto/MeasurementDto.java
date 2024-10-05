package ru.flynt3650.project.weather_sensor.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MeasurementDto {
    @NotNull
    private Long id;

    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    private Double temperature;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private Boolean isRaining;

    private SensorDto sensorDto;

//    public Long getId() {
//        return id;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean isRaining() {
        return isRaining;
    }

    public void setRaining(Boolean isRaining) {
        this.isRaining = isRaining;
    }

    public SensorDto getSensor() {
        return sensorDto;
    }

    public void setSensor(SensorDto sensor) {
        this.sensorDto = sensor;
    }
}
