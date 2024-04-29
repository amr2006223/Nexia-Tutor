package com.example.identityservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.identityservice.models.UserCredentials;
@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials,String>{
    public Optional<UserCredentials> findByUsername(String username);
}
