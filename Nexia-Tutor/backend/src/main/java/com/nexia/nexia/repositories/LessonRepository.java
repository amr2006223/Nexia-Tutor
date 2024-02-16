package com.nexia.nexia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexia.nexia.models.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {

}
