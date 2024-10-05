package ru.flynt3650.project.weather_sensor.controllers;


import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.flynt3650.project.weather_sensor.dto.MeasurementDto;
import ru.flynt3650.project.weather_sensor.dto.SensorDto;
import ru.flynt3650.project.weather_sensor.models.Measurement;
import ru.flynt3650.project.weather_sensor.models.Sensor;
import ru.flynt3650.project.weather_sensor.services.MeasurementService;
import ru.flynt3650.project.weather_sensor.services.SensorService;
import ru.flynt3650.project.weather_sensor.util.MeasurementExceptionResponse;
import ru.flynt3650.project.weather_sensor.util.MeasurementNotCreatedException;
import ru.flynt3650.project.weather_sensor.util.MeasurementNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorService sensorService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<MeasurementDto> getMeasurements() {
        return measurementService
                .findAll()
                .stream()
                .map(this::toMeasurementDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MeasurementDto getOne(@PathVariable("id") long id) {
        return toMeasurementDto(measurementService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDto measurementDto,
                                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (var error : errors)
                errorMessage
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");

            throw new MeasurementNotCreatedException(errorMessage.toString());
        }


        String sensorName = measurementDto.getSensor().getName();
        Long sensorId = sensorService.findSensorIdByName(sensorName);
        measurementDto.getSensor().setId(sensorId);

        measurementService.save(toMeasurement(measurementDto));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<MeasurementExceptionResponse> handleException(MeasurementNotFoundException e) {
        MeasurementExceptionResponse response = new MeasurementExceptionResponse(
                "Measurement not found.", System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementExceptionResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementExceptionResponse response = new MeasurementExceptionResponse(
                e.getMessage(), System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private MeasurementDto toMeasurementDto(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDto.class);
    }


    private Measurement toMeasurement(MeasurementDto measurementDto) {
        return modelMapper.map(measurementDto, Measurement.class);
    }


    private SensorDto toSensorDto(Sensor sensor) {
        return modelMapper.map(sensor, SensorDto.class);
    }

    private Sensor toSensor(SensorDto sensorDto) {
        return modelMapper.map(sensorDto, Sensor.class);
    }
}
