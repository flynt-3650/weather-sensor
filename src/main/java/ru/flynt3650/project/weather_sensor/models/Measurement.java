package ru.flynt3650.project.weather_sensor.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "measurement")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "temperature")
    @Min(value = -70)
    @Max(value = 70)
    private int temperature;

    @Column(name = "is_raining")
    private boolean isRaining;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @JsonIgnoreProperties("measurements")
    private Sensor sensor;

    public Measurement() {
    }

    public Measurement(int temperature, boolean isRaining, Sensor sensor) {
        this.temperature = temperature;
        this.isRaining = isRaining;
        this.sensor = sensor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
