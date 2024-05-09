package com.nexia.nexia.controlles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.nexia.nexia.models.Game;
import com.nexia.nexia.services.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;

@RestController
@RequestMapping("/nexia-tutor/api/games")
public class GameController {
    @Autowired
    private GameService gameService;


    @PostMapping("/token/{token}")
    public ResponseEntity<?> getGamesForUser(@PathVariable String token) {
        List<Game> userGames = gameService.getGamesForUser(token);
        if (userGames == null)
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        if (userGames.isEmpty())
            return new ResponseEntity<String>("No Games Found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<Game>>(userGames, HttpStatus.OK);
        // return new ResponseEntity<>(HttpStatus.OK);
    }



}
