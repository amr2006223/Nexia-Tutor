package com.nexia.nexia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexia.nexia.models.DyslexiaType;

@Repository
public interface DyslexiaTypeRepository extends JpaRepository<DyslexiaType, String> {

}
