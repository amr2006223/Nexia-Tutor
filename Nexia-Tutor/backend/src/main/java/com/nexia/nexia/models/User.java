package com.nexia.nexia.models;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(unique = true)
    private String username;
    // @JsonIgnore
    private String password;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private String nationality;
    private boolean gender;
    private String role;
    @Column(nullable = true)
    private String token;
    private String parentPin;

  
    @ManyToMany(targetEntity = DyslexiaType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_dyslexia_types", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "dyslexia_type_id"))
    private Set<DyslexiaType> dyslexiaTypes;

    public User() {
    }

    public User(String id, String username, String password, Date birthDate, String nationality, boolean gender,
            Set<DyslexiaType> dyslexia_types, String role,String parentPin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.gender = gender;
        this.dyslexiaTypes = dyslexia_types;
        this.role = role;
        this.parentPin = parentPin;
    }

    public String getParentPin() {
        return parentPin;
    }

    public void setParentPin(String parentPin) {
        this.parentPin = parentPin;
    }

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

    public String getRole() {
        return role;
    }

    public void settRole(String role) {
        this.role = role;
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

    public Set<DyslexiaType> getDyslexia_types() {
        return dyslexiaTypes;
    }

    public void setDyslexia_types(Set<DyslexiaType> dyslexia_types) {
        this.dyslexiaTypes = dyslexia_types;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<SimpleGrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority(this.role)));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
