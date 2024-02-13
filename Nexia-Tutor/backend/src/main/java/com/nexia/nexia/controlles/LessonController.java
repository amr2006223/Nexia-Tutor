package com.nexia.nexia.controlles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexia.nexia.services.LessonsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonsService lessonsService;

    // TODO: add a method to get all lessons
    // @GetMapping("/getAllLessons")
    // public ResponseEntity getAllLessons() {
    // Object jsonResponse = lessonsService.getAllLessons();
    // if (jsonResponse != null) {
    // return ResponseEntity.ok(jsonResponse);
    // } else {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed
    // to retrieve data");
    // }

    // }

}
