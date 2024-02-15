package com.example.report_generation.report_generation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.report_generation.report_generation.models.User;

public interface UserRepository extends JpaRepository<User, String> {
    // You can add custom query methods here if needed

}