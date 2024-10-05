package ru.flynt3650.project.weather_sensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.flynt3650.project.weather_sensor.models.Sensor;
import ru.flynt3650.project.weather_sensor.repositories.SensorRepository;

import java.util.List;

@Service
public class SensorService {

    private SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Long findSensorIdByName(String name) {
        return sensorRepository.findSensorIdByName(name).orElseThrow(RuntimeException::new);
    }

    public Sensor findById(Long id) {
        return sensorRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
