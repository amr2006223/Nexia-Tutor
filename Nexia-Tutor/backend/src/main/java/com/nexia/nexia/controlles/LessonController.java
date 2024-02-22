package com.nexia.nexia.controlles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nexia.nexia.models.Image;
import com.nexia.nexia.models.Keyword;
import com.nexia.nexia.models.Lesson;
import com.nexia.nexia.services.LessonService;
import java.util.List;

@RestController
@RequestMapping("/api/lessons/json")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping("/addLessons")
    public ResponseEntity<List<Lesson>> saveLessons(@RequestBody List<Lesson> lessons) {
        lessonService.saveLessons(lessons);
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @PostMapping("/addLesson")
    public ResponseEntity<Lesson> saveLesson(@RequestBody Lesson lesson) {
        try {
            lessonService.addEntity(lesson);
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{lessonName}/{keyword}/images")
    public ResponseEntity<List<Image>> getLessonImages(@PathVariable String lessonName, @PathVariable String keyword) {
        try {
            List<Image> images = lessonService.getLessonImages(lessonName, keyword);
            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allLessons")
    public ResponseEntity<List<Lesson>> getLessons() {
        List<Lesson> lessons = lessonService.getLessons();
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
        List<Keyword> keywords = lessonService.getEntityById(lessonName).getKeywords();
        return new ResponseEntity<>(keywords, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{lessonName}")
    public ResponseEntity<String> deleteLesson(@PathVariable String lessonName) {
        try {
            lessonService.deleteEntity(lessonName);
            return new ResponseEntity<>("Lesson deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete lesson: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<String> editLesson( @RequestBody Lesson updatedLesson) {
        try {
            lessonService.updateEntity(updatedLesson);
            return new ResponseEntity<>("Lesson edited successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to edit lesson: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
