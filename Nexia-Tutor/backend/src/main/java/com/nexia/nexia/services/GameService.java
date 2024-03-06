package com.nexia.nexia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nexia.nexia.models.DyslexiaType;
import com.nexia.nexia.models.Game;
import com.nexia.nexia.models.Lesson;
import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.DyslexiaTypeRepository;
import com.nexia.nexia.repositories.GameRepository;
import com.nexia.nexia.services.iservices.IGameService;

import java.util.*;

@Service
public class GameService extends CrudOperations<Game, Long, GameRepository> implements IGameService {

    @Autowired
    private UserService userService;
    @Autowired
    private LessonService lessonJsonService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private DyslexiaTypeRepository dyslexiaTypeRepository;

    public GameService(GameRepository repository) {
        super(repository);
    }

    @Override
    public Map<String, Object> getGamesForLesson(String lessonName, String token) {
        try {

            // get user
            User user = userService.getEntityById(token);
            if (user == null)
                return null;
            // get lesson and get all keywords
            Lesson lessonDetails = lessonJsonService.getEntityById(lessonName);
            if (lessonDetails == null)
                return null;
            // get user dyslexia types
            Set<DyslexiaType> dyslexiaTypes = user.getDyslexia_types();
            // get all games for dyslexia types (loop)
            List<Game> gamesList = new ArrayList<>();
            for (DyslexiaType dyslexiaType : dyslexiaTypes) {
                List<Game> gamesForType = getGamesForDyslexiaType(dyslexiaType.getId());
                gamesList.addAll(gamesForType);
            }
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("lesson_name", lessonDetails.getLessonName());
            jsonResponse.put("keywords", lessonDetails.getKeywords());
            jsonResponse.put("games", gamesList);
            return jsonResponse;
        } catch (Exception e) {
            return null;
        }
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
