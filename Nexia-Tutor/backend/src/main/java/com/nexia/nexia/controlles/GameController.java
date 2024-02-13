package com.nexia.nexia.controlles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexia.nexia.services.GameService;
import com.nexia.nexia.services.KeywordService;
import com.nexia.nexia.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private KeywordService keywordService;

    // TODO: get all games for specific lesson and specific dyslexia type
    @PostMapping("/{lessonName}")
    public ResponseEntity getGamesForLesson(@PathVariable String lessonName,
            @RequestBody Map<String, String> body) {
        String id = body.get("id");
        long userID = Long.parseLong(id);
        Object jsonResponse = gameService.getGamesForLesson(lessonName, userID);
        if (jsonResponse != null) {
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve data");
        }

    }
}
