package com.nexia.nexia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nexia.nexia.models.DyslexiaType;
import com.nexia.nexia.models.Game;
import com.nexia.nexia.models.Lesson;
import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.DyslexiaTypeRepository;
import com.nexia.nexia.repositories.GameRepository;
import java.util.*;

@Service
public class GameService extends CrudOperations<Game, Long, GameRepository> {

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

    public Map<String, Object> getGamesForLesson(String LessonName, String userId) {
        try {

            // get user
            User user = userService.getEntityById(userId);
            if (user == null) return null;
            // get lesson and get all keywords
            Lesson lessonDetails = lessonJsonService.getEntityById(LessonName);
            if (lessonDetails == null) return null;
            // get user dyslexia types
            List<DyslexiaType> dyslexiaTypes = user.getDyslexia_types();
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
}
