package com.nexia.nexia.models;

import org.hibernate.annotations.GenericGenerator;


import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;

public class Image {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String image_url;

    public Image(String id, String image_url) {
        this.id = id;
        this.image_url = image_url;
    }

    public Image() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
