package com.nexia.nexia.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexia.nexia.models.UserPerformance;

@Repository
public interface UserPerformanceRepository extends JpaRepository<UserPerformance, String> {
    Optional<List<UserPerformance>> findByUserId(String userId);

    Optional<List<UserPerformance>> findByGameId(long gameId);

    Optional<List<UserPerformance>> findByGameIdAndUserId(long gameId, String userId);

    Optional<List<UserPerformance>> findByUserIdAndPerformanceDateBetween(String userId, Date startDate, Date endDate);

    Optional<List<UserPerformance>> findByPerformanceDateBetween(Date startDate, Date endDate);
}