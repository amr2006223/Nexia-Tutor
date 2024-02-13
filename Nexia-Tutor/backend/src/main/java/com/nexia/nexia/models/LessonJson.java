package com.nexia.nexia.models;

import java.util.List;

public class LessonJson {
    private String lessonName;

    private List<Keyword> keywords;

    public LessonJson() {
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
