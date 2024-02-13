package com.nexia.nexia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nexia.nexia.models.DyslexiaType;
import com.nexia.nexia.models.Game;
import com.nexia.nexia.models.Keyword;
import com.nexia.nexia.models.Lesson;
import com.nexia.nexia.models.LessonJson;
import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.DyslexiaTypeRepository;
import com.nexia.nexia.repositories.GameRepository;
import java.util.*;

@Service
public class GameService {
    @Autowired
    private UserService userService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private LessonsService lessonsService;
    @Autowired
    private LessonJsonService lessonJsonService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private DyslexiaTypeRepository dyslexiaTypeRepository;

    public Map<String, Object> getGamesForLesson(String LessonName, Long userId) {
        // 0.get user
        User user = userService.getUserById(userId);
        // get lesson and get all keywords
        LessonJson lessonDetails = lessonJsonService.getLessonByName(LessonName);

        // 2. get user dyslexia types
        List<DyslexiaType> dyslexiaTypes = user.getDyslexia_types();

        // 3. get all games for dyslexia types (loop)
        List<Map<String, Object>> gamesList = new ArrayList<>();

        for (DyslexiaType dyslexiaType : dyslexiaTypes) {
            List<Game> gamesForType = getGamesForDyslexiaType(dyslexiaType.getId());

            for (Game game : gamesForType) {
                Map<String, Object> gameInfo = new HashMap<>();
                gameInfo.put("game_id", game.getId());
                gameInfo.put("game_name", game.getGame_name());
                gamesList.add(gameInfo);
            }
        }
        Map<String, Object> jsonResponse = new HashMap<>();

        jsonResponse.put("lesson_name", lessonDetails.getLessonName());
        jsonResponse.put("keywords", lessonDetails.getKeywords());
        jsonResponse.put("games", gamesList);

        return jsonResponse;

    }

    // TODO: get all games for specific dyslexia type
    List<Game> getGamesForDyslexiaType(Long dyslexiaTypeId) {
        DyslexiaType dyslexiaType = dyslexiaTypeRepository.findById(dyslexiaTypeId).orElse(null);
        if (dyslexiaType == null) {
            return null;
        } else {
            return gameRepository.findByDyslexiaType(dyslexiaType);
        }

    }
    // * * JPQL Object java:
    // TODO: select all from Table game where DeslexiaTypeID=x

}
