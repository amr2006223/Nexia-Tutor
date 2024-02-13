package com.nexia.nexia.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String game_name;

    @ManyToOne(targetEntity = DyslexiaType.class, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "dyslexia_type_id", referencedColumnName = "id")
    private DyslexiaType dyslexiaType;

    public Game(Long id, String game_name, DyslexiaType dyslexia_type) {
        this.id = id;
        this.game_name = game_name;
        this.dyslexiaType = dyslexia_type;
    }

    public Game() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public DyslexiaType getDyslexia_type() {
        return dyslexiaType;
    }

    public void setDyslexia_type(DyslexiaType dyslexia_type) {
        this.dyslexiaType = dyslexia_type;
    }
}
