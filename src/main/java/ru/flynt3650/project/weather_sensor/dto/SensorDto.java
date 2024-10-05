package ru.flynt3650.project.weather_sensor.dto;

import jakarta.validation.constraints.NotNull;

public class SensorDto {

    private Long id;

    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SensorDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
