package com.nexia.nexia.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexia.nexia.models.UserPerformance;

@Repository
public interface UserPerformanceRepository extends JpaRepository<UserPerformance, String> {
    Optional<List<UserPerformance>> findAllByUserId(String userId);

    Optional<List<UserPerformance>> findAllByGameId(long gameId);

    Optional<List<UserPerformance>> findAllByGameIdAndUserId(long gameId, String userId);

    Optional<List<UserPerformance>> findAllByUserIdAndPerformanceDateBetween(String userId, Date startDate, Date endDate);

    Optional<List<UserPerformance>> findAllByPerformanceDateBetween(Date startDate, Date endDate);
}