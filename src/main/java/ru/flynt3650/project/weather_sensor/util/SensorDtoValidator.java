package ru.flynt3650.project.weather_sensor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.flynt3650.project.weather_sensor.dto.SensorDto;
import ru.flynt3650.project.weather_sensor.models.Sensor;
import ru.flynt3650.project.weather_sensor.services.SensorService;

import java.util.List;

@Component
public class SensorDtoValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorDtoValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDto sensor = (SensorDto) target;
        String name = sensor.getName();

        List<Sensor> existingSensors = sensorService.findSensorsByName(name);
        if (!existingSensors.isEmpty()) {
            errors.rejectValue("name", "", "Sensor with name '" + name + "' already exists!");
        }
    }
}
