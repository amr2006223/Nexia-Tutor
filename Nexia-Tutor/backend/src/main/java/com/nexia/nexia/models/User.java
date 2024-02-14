package com.nexia.nexia.models;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(unique = true)
    private String username;

    private String password;
    private Date birthDate;
    private String nationality;
    private boolean gender;

    @ManyToMany(targetEntity = DyslexiaType.class)
    @JoinTable(name = "user_dyslexia_types", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "dyslexia_type_id"))
    private List<DyslexiaType> dyslexia_types;
    
    public User(){}

    public User(String id, String username, String password, Date birthDate, String nationality, boolean gender,
            List<DyslexiaType> dyslexia_types) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.gender = gender;
        this.dyslexia_types = dyslexia_types;
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

    public List<DyslexiaType> getDyslexia_types() {
        return dyslexia_types;
    }

    public void setDyslexia_types(List<DyslexiaType> dyslexia_types) {
        this.dyslexia_types = dyslexia_types;
    }
    


}
