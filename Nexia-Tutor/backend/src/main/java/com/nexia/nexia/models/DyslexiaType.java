package com.nexia.nexia.models;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;

@Entity
public class DyslexiaType {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String dyslexia_type_name;

    public DyslexiaType() {
    }

    public DyslexiaType(String id, String dyslexia_type_name) {
        this.id = id;
        this.dyslexia_type_name = dyslexia_type_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDyslexia_type_name() {
        return dyslexia_type_name;
    }

    public void setDyslexia_type_name(String dyslexia_type_name) {
        this.dyslexia_type_name = dyslexia_type_name;
    }

}
