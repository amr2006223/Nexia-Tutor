package com.nexia.nexia.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexia.nexia.models.UserPerformance;

@Repository
public interface UserPerformanceRepository extends JpaRepository<UserPerformance, String> {
    List<UserPerformance> findByUserId(String userId);

    List<UserPerformance> findByGameId(String gameId);

    List<UserPerformance> findByGameAndUser(String gameId, String userId);

    List<UserPerformance> findByUserIdAndPerformanceDateBetween(String userId, Date startDate, Date endDate);

    List<UserPerformance> findByPerformanceDateBetween(Date startDate, Date endDate);
}