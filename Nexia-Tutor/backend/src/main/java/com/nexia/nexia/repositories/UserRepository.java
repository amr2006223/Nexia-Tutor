package com.nexia.nexia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexia.nexia.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
