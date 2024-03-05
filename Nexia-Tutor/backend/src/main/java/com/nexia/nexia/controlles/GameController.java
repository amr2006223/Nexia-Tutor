package com.nexia.nexia.controlles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexia.nexia.models.Game;
import com.nexia.nexia.services.GameService;
import com.nexia.nexia.services.jwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;

@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private jwtService jwtService;

    @PostMapping("/{lessonName}")
    public ResponseEntity<?> getGamesForLesson(@PathVariable String lessonName,
            @RequestBody Map<String, String> body) {
        String id = body.get("id");
        Object jsonResponse = gameService.getGamesForLesson(lessonName, id);
        if (jsonResponse != null) {
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve data");
        }
    }
    @PostMapping("/token/{token}")
    public ResponseEntity<?> getGamesForUser(@PathVariable String token) {
        String id = jwtService.extractUUID(token);
        System.out.println(id);
        List<Game> userGames = gameService.getGamesForUser(id);
        if(userGames == null) return new ResponseEntity<String>("User Not Found",HttpStatus.NOT_FOUND);
        if(userGames.isEmpty()) return new ResponseEntity<String>("No Games Found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<Game>>(userGames,HttpStatus.OK);
    }
    
    @PostMapping("/gen/{token}")
    public String  test(@PathVariable String token) {
       
        String id = jwtService.generateToken(token);
       return id;
    }
    

}
