package com.nexia.nexia.models;

import java.util.List;

public class Lesson {
    private String lessonName;

    private List<Keyword> keywords;

    public Lesson() {
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

}
