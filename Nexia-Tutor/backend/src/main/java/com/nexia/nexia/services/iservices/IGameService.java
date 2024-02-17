package com.nexia.nexia.services.iservices;

import java.util.Map;

public interface IGameService {

    Map<String, Object> getGamesForLesson(String lessonName, String userId);

}
