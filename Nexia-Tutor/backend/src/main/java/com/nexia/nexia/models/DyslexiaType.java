package com.nexia.nexia.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DyslexiaType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dyslexia_type_name;

    public DyslexiaType() {
    }

    public DyslexiaType(Long id, String dyslexia_type_name) {
        this.id = id;
        this.dyslexia_type_name = dyslexia_type_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDyslexia_type_name() {
        return dyslexia_type_name;
    }

    public void setDyslexia_type_name(String dyslexia_type_name) {
        this.dyslexia_type_name = dyslexia_type_name;
    }
}
