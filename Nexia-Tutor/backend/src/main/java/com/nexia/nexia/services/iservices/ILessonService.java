package com.nexia.nexia.services.iservices;

import com.nexia.nexia.models.Image;
import com.nexia.nexia.models.Lesson;

import java.util.List;

public interface ILessonService {

    void saveLessons(List<Lesson> lessons);

    List<Image> getLessonImages(String lessonName, String keyword);

    List<Lesson> getLessons();

    List<String> getLessonNames();

}
