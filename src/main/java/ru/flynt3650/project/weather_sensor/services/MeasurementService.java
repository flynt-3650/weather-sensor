package ru.flynt3650.project.weather_sensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flynt3650.project.weather_sensor.models.Measurement;
import ru.flynt3650.project.weather_sensor.repositories.MeasurementRepository;
import ru.flynt3650.project.weather_sensor.util.MeasurementNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public Measurement findById(long id) {
        return measurementRepository.findById(id).orElseThrow(MeasurementNotFoundException::new);
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public Long findRainyDays() {
        return measurementRepository.countRainyDays();
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
    }
}
