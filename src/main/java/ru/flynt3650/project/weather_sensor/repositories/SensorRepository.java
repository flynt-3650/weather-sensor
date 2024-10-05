package ru.flynt3650.project.weather_sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.flynt3650.project.weather_sensor.models.Sensor;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Query("select s.id from Sensor s where s.name = :name")
    Optional<Long> findSensorIdByName(@Param("name") String name);
}
