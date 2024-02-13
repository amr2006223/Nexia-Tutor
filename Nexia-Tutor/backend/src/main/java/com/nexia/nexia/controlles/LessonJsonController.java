package com.nexia.nexia.controlles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nexia.nexia.models.Image;
import com.nexia.nexia.models.Keyword;
import com.nexia.nexia.models.LessonJson;
import com.nexia.nexia.services.LessonJsonService;


import java.util.List;


@RestController
@RequestMapping("/lessons/json")
public class LessonJsonController {

    @Autowired
    private LessonJsonService lessonService;

    @PostMapping("/save")
    public ResponseEntity<List<LessonJson>> saveLessons(@RequestBody List<LessonJson> lessons) {
        lessonService.saveLessons(lessons, false);
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @PostMapping("/saveLesson")
    public ResponseEntity<LessonJson> saveLesson(@RequestBody LessonJson lesson) {
        try {
            lessonService.saveLesson(lesson, false);
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PostMapping("/save-with-images")
    // public ResponseEntity<List<LessonJson>> saveLessonsWithImages(@RequestBody
    // LessonWithImagesRequest request) {
    // try {
    // lessonService.saveLessonWithImages(request.getLessonJson(),
    // request.getImageUrls());
    // return new ResponseEntity<>(List.of(request.getLessonJson()), HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    @GetMapping("/{lessonName}/{keyword}/images")
    public ResponseEntity<List<Image>> getLessonImages(@PathVariable String lessonName, @PathVariable String keyword) {
        try {
            List<Image> images = lessonService.getLessonImages(lessonName, keyword);
            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<LessonJson>> getLessons() {
        List<LessonJson> lessons = lessonService.getLessons();
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getLessonNames() {
        try {
            List<String> lessonNames = lessonService.getLessonNames();
            return new ResponseEntity<>(lessonNames, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception and return an error response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{lessonName}/keywords")
    public ResponseEntity<List<Keyword>> getLessonKeywords(@PathVariable String lessonName) {
        List<Keyword> keywords = lessonService.getLessonByName(lessonName).getKeywords();
        return new ResponseEntity<>(keywords, HttpStatus.OK);
    }

    @DeleteMapping("/{lessonName}")
    public ResponseEntity<String> deleteLesson(@PathVariable String lessonName) {
        try {
            lessonService.deleteLesson(lessonName);
            return new ResponseEntity<>("Lesson deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete lesson: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{lessonName}")
    public ResponseEntity<String> editLesson(@PathVariable String lessonName, @RequestBody LessonJson updatedLesson) {
        try {
            lessonService.editLesson(lessonName, updatedLesson);
            return new ResponseEntity<>("Lesson edited successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to edit lesson: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
