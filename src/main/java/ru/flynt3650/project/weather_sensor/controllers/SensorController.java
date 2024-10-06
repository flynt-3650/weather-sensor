package ru.flynt3650.project.weather_sensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.flynt3650.project.weather_sensor.dto.SensorDto;
import ru.flynt3650.project.weather_sensor.models.Sensor;
import ru.flynt3650.project.weather_sensor.services.SensorService;
import ru.flynt3650.project.weather_sensor.util.SensorDtoValidator;
import ru.flynt3650.project.weather_sensor.util.SensorExceptionResponse;
import ru.flynt3650.project.weather_sensor.util.exceptions.SensorNotCreatedException;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final SensorDtoValidator sensorDtoValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, SensorDtoValidator sensorDtoValidator,
                            ModelMapper modelMapper) {

        this.sensorService = sensorService;
        this.sensorDtoValidator = sensorDtoValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDto sensorDto,
                                                     BindingResult bindingResult) {

        sensorDtoValidator.validate(sensorDto, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (var error : errors)
                errorMessage
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");

            throw new SensorNotCreatedException(errorMessage.toString());
        }

        sensorService.save(toSensor(sensorDto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<SensorExceptionResponse> handleException(SensorNotCreatedException e) {
        SensorExceptionResponse response = new SensorExceptionResponse(
                e.getMessage(), System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor toSensor(SensorDto sensorDto) {
        return modelMapper.map(sensorDto, Sensor.class);
    }
}
