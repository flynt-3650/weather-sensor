package ru.flynt3650.project.weather_sensor.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.flynt3650.project.weather_sensor.dto.MeasurementDto;
import ru.flynt3650.project.weather_sensor.models.Measurement;
import ru.flynt3650.project.weather_sensor.services.MeasurementService;
import ru.flynt3650.project.weather_sensor.util.MeasurementExceptionResponse;
import ru.flynt3650.project.weather_sensor.util.MeasurementNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
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

    @ExceptionHandler
    private ResponseEntity<MeasurementExceptionResponse> handleException(MeasurementNotFoundException e) {
        MeasurementExceptionResponse response = new MeasurementExceptionResponse(
                "Measurement not found exception", System.currentTimeMillis()
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
