package com.nexia.nexia.models;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class DyslexiaType {

    @Id
    private String id;
    private String dyslexia_type_name;
    @ManyToMany(mappedBy = "dyslexiaTypes")
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
