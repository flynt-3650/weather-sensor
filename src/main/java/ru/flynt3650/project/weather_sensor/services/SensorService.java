package ru.flynt3650.project.weather_sensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.flynt3650.project.weather_sensor.util.exceptions.SensorNotFoundException;
import ru.flynt3650.project.weather_sensor.models.Sensor;
import ru.flynt3650.project.weather_sensor.repositories.SensorRepository;

import java.util.List;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Long findSensorIdByName(String name) {
        return sensorRepository.findSensorIdByName(name)
                .orElseThrow(() -> new SensorNotFoundException("Sensor with name '" + name + "' not found"));
    }

    public List<Sensor> findSensorsByName(String name) {
        return sensorRepository.findSensorsByName(name)
                .orElseThrow(() -> new SensorNotFoundException("No sensors found with name '" + name + "'"));
    }

    public Sensor findById(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new SensorNotFoundException("Sensor with id '" + id + "' not found"));
    }

    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
