package com.example.report_generation.report_generation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.report_generation.report_generation.models.DyslexiaCategory;

public interface DyslexiaCategoryRepository extends JpaRepository<DyslexiaCategory, String> {
    // You can add custom query methods here if needed
    DyslexiaCategory findCategoryByName(@Param("name") String name);
}