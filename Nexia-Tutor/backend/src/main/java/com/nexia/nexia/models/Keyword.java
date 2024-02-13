package com.nexia.nexia.models;

import java.util.List;

public class Keyword {

    private String keyword_name;
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Keyword() {
    }

    public Keyword(String keyword_name) {

        this.keyword_name = keyword_name;

    }

    public String getKeyword_name() {
        return keyword_name;
    }

    public void setKeyword_name(String keyword_name) {
        this.keyword_name = keyword_name;
    }
}
