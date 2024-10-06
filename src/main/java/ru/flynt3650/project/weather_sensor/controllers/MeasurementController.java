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
import ru.flynt3650.project.weather_sensor.models.Measurement;
import ru.flynt3650.project.weather_sensor.services.MeasurementService;
import ru.flynt3650.project.weather_sensor.services.SensorService;
import ru.flynt3650.project.weather_sensor.util.MeasurementExceptionResponse;
import ru.flynt3650.project.weather_sensor.util.SensorExceptionResponse;
import ru.flynt3650.project.weather_sensor.util.exceptions.MeasurementNotCreatedException;
import ru.flynt3650.project.weather_sensor.util.exceptions.MeasurementNotFoundException;
import ru.flynt3650.project.weather_sensor.util.exceptions.SensorNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorService sensorService,
                                 ModelMapper modelMapper) {

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
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDto measurementDto,
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

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Long> getRainyDaysCount() {
        Long count = measurementService.findRainyDays();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementExceptionResponse> handleMeasurementNotFoundException(MeasurementNotFoundException e) {
        MeasurementExceptionResponse response = new MeasurementExceptionResponse(
                e.getMessage(), System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementExceptionResponse> handleMeasurementNotCreatedException(MeasurementNotCreatedException e) {
        MeasurementExceptionResponse response = new MeasurementExceptionResponse(
                e.getMessage(), System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<SensorExceptionResponse> handleSensorNotFoundException(SensorNotFoundException e) {
        SensorExceptionResponse response = new SensorExceptionResponse(
                e.getMessage(), System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private MeasurementDto toMeasurementDto(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDto.class);
    }

    private Measurement toMeasurement(MeasurementDto measurementDto) {
        return modelMapper.map(measurementDto, Measurement.class);
    }
}
