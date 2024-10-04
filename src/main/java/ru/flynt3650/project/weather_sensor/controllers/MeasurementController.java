package ru.flynt3650.project.weather_sensor.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.flynt3650.project.weather_sensor.models.Measurement;
import ru.flynt3650.project.weather_sensor.services.MeasurementService;
import ru.flynt3650.project.weather_sensor.util.MeasurementExceptionResponse;
import ru.flynt3650.project.weather_sensor.util.MeasurementNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping()
    public List<Measurement> getAll() {
        return measurementService.findAll();
    }

    @GetMapping("/{id}")
    public Measurement getOne(@PathVariable("id") long id) {
        return measurementService.findById(id);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementExceptionResponse> handleException(MeasurementNotFoundException e) {
        MeasurementExceptionResponse response = new MeasurementExceptionResponse(
                "Measurement not found exception", System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
