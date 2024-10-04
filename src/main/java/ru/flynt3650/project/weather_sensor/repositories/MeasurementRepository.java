package ru.flynt3650.project.weather_sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.flynt3650.project.weather_sensor.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
}
