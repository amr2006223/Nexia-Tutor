package com.nexia.nexia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.nexia.nexia.models.DyslexiaType;
import com.nexia.nexia.models.Game;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByDyslexiaType(DyslexiaType dyslexia_type);

    // @Query("SELECT G FROM GAME G WHERE G.")
    // List<Game> getGamesByDeslexiaID(@Param("DeslexiaTypeID") Long
    // DeslexiaTypeID);

}
