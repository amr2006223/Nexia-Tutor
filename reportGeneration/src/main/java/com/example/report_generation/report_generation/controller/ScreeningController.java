package com.example.report_generation.report_generation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.report_generation.report_generation.models.DyslexiaCategory;
import com.example.report_generation.report_generation.service.ScreeningService;

@RestController
@RequestMapping("/report-generation")
public class ScreeningController {
    @Autowired
    ScreeningService _screeningService;

    @GetMapping("/get/average")
    public ResponseEntity<List<DyslexiaCategory>> getAverage() {
        return new ResponseEntity<List<DyslexiaCategory>>(_screeningService.getAverageFromScreeningService(),
                HttpStatus.OK);
    }
}
