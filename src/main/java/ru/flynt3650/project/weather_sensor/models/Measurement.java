package ru.flynt3650.project.weather_sensor.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "measurement")
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "temperature")
    @Min(value = -100)
    @Max(value = 100)
    private Double temperature;

    @NotNull
    @Column(name = "is_raining")
    private Boolean isRaining;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    @JsonBackReference
    private Sensor sensor;

    public Measurement() {
    }

    public Measurement(double temperature, boolean isRaining, LocalDateTime createdAt, Sensor sensor) {
        this.temperature = temperature;
        this.isRaining = isRaining;
        this.createdAt = createdAt;
        this.sensor = sensor;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return Objects.equals(id, that.id)
                && Objects.equals(temperature, that.temperature)
                && Objects.equals(isRaining, that.isRaining)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(sensor, that.sensor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, temperature, isRaining, createdAt, sensor);
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", isRaining=" + isRaining +
                ", createdAt=" + createdAt +
                ", sensor=" + sensor +
                '}';
    }
}
