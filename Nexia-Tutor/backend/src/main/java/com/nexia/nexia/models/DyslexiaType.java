package com.nexia.nexia.models;

import java.io.Serializable;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class DyslexiaType implements Serializable{

    @Id
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
