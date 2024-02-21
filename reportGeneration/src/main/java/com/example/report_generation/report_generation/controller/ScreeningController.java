package com.example.report_generation.report_generation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.report_generation.report_generation.models.DyslexiaCategory;
import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.service.ScreeningService;



@RestController
public class ScreeningController {
    @Autowired
    ScreeningService _screeningService;
    
    @GetMapping("/get/average")
    public ResponseEntity<List<DyslexiaCategory>> getAverage(){
        return new ResponseEntity<List<DyslexiaCategory>> (_screeningService.getAverageFromScreeningService(),HttpStatus.OK);
    }
    
    @PostMapping("/post/smth")
    public ResponseEntity<String> postSmth() {
        return new ResponseEntity<String>(_screeningService.postSomethingAndGetResponse(), HttpStatus.OK);
    }

    @PostMapping("/create/importantUser")
    public User CreateUserWithAlphaAwarness(@RequestBody Map<String, Object> body) {
        return _screeningService.getUserWithAlphaAwarness(body.get("userId").toString());
        // return new SomeData();
    }
    
    
}
