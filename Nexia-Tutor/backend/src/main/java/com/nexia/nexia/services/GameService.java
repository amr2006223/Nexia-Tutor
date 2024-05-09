package com.nexia.nexia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nexia.nexia.models.DyslexiaType;
import com.nexia.nexia.models.Game;
import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.DyslexiaTypeRepository;
import com.nexia.nexia.repositories.GameRepository;


import java.util.*;

@Service
public class GameService extends CrudOperations<Game, Long, GameRepository> {

    @Autowired
    private UserService userService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private DyslexiaTypeRepository dyslexiaTypeRepository;

    public GameService(GameRepository repository) {
        super(repository);
    }


    private List<Game> getGamesForDyslexiaType(String dyslexiaTypeId) {
        DyslexiaType dyslexiaType = dyslexiaTypeRepository.findById(dyslexiaTypeId).orElse(null);
        if (dyslexiaType == null) {
            return null;
        } else {
            return gameRepository.findByDyslexiaType(dyslexiaType);
        }
    }
    
    public List<Game> getGamesForUser(String token){
        User user = userService.getEntityById(token);
        if(user == null) return null;
        List<Game> userGames = new ArrayList<Game>();
        for (DyslexiaType dyslexiaType : user.getDyslexia_types()) {
            userGames.addAll(getGamesForDyslexiaType(dyslexiaType.getId()));
        }
        return userGames;
    }

}
