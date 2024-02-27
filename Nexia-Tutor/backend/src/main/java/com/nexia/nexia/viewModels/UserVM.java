package com.nexia.nexia.viewModels;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nexia.nexia.models.DyslexiaType;


public class UserVM {
    
    private String id;
    private String username;
    private String password;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date birthDate;
    private String nationality;
    private boolean gender;
    private String role;
    private String token;
    public List<DyslexiaType> getDyslexiaTypes() {
        return dyslexiaTypes;
    }
    public void setDyslexiaTypes(List<DyslexiaType> dyslexiaTypes) {
        this.dyslexiaTypes = dyslexiaTypes;
    }
    private List<DyslexiaType> dyslexiaTypes;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public boolean isGender() {
        return gender;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    
}
