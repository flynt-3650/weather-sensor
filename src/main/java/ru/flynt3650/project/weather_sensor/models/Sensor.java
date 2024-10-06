package ru.flynt3650.project.weather_sensor.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(min = 3, max = 30, message = "Invalid sensor name length")
    private String name;

    @OneToMany(mappedBy = "sensor")
    @JsonManagedReference // not sure
    private List<Measurement> measurements;

    public Sensor() {
    }

    public Sensor(String name, List<Measurement> measurements) {
        this.name = name;
        this.measurements = measurements;
    }

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

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return Objects.equals(id, sensor.id)
                && Objects.equals(name, sensor.name)
                && Objects.equals(measurements, sensor.measurements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, measurements);
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", measurements=" + measurements +
                '}';
    }
}
