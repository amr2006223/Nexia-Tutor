package com.nexia.nexia.services;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import com.nexia.nexia.models.UserPerformance;
import com.nexia.nexia.repositories.UserPerformanceRepository;

public class AnalysisService {
    @Autowired
    private UserPerformanceRepository userPerformanceRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;

    public void save(String token, String gameId, Short misses, Long time, Date endDate) {
        UserPerformance userPerformance = new UserPerformance();
        userPerformance.setUser(userService.getEntityById(token));
        userPerformance.setGame(gameService.getEntityById(Long.parseLong(gameId)));// error
        userPerformance.setMisses(misses);
        userPerformance.setTimeTakenInSeconds(time);
        userPerformance.setPerformanceDate(endDate);
    }

    public Map<String, Double> calculateTotalMissesForUser(String userId, Date startDate, Date endDate) {
        
        List<UserPerformance> userPerformances = userPerformanceRepository.findByUserIdAndPerformanceDateBetween(userId,
                startDate, endDate).orElse(Collections.emptyList());
        Map<String, Double> result = new HashMap<>();

        if (userPerformances.isEmpty()) {
            return result;
        }

        int totalMisses = 0;
        for (UserPerformance userList : userPerformances) {
            totalMisses += userList.getMisses();
        }

        result.put("TotalMisses", (double) totalMisses);

        return result;
    }

    public Map<String, Double> calculateTotalTimeTakenForUser(String userId, Date startDate, Date endDate) {
        List<UserPerformance> userPerformances = userPerformanceRepository.findByUserIdAndPerformanceDateBetween(userId,
                startDate, endDate).orElse(Collections.emptyList());
        Map<String, Double> result = new HashMap<>();

        if (userPerformances.isEmpty()) {
            return result;
        }

        long totalTimeTaken = 0;
        for (UserPerformance userList : userPerformances) {
            totalTimeTaken += userList.getTimeTakenInSeconds();
        }

        result.put("TotalTimeTaken", (double) totalTimeTaken);

        return result;
    }

    public Map<String, Double> calculateMaxMinMissesForUser(String userId, Date startDate, Date endDate) {
        List<UserPerformance> performances = userPerformanceRepository.findByUserIdAndPerformanceDateBetween(userId,
                startDate, endDate).orElse(Collections.emptyList());

        Map<String, Double> result = new HashMap<>();
        if (performances.isEmpty()) {
            return result;
        }

        // Finding max and min misses
        short maxMisses = 0;
        short minMisses = Short.MAX_VALUE;
        for (UserPerformance performance : performances) {
            short misses = performance.getMisses();
            if (misses > maxMisses) {
                maxMisses = misses;
            }
            if (misses < minMisses) {
                minMisses = misses;
            }
        }

        result.put("maxMisses", (double) maxMisses);
        result.put("minMisses", (double) minMisses);

        return result;
    }

    public Map<String, Double> calculateMaxMinTimeTakenForUser(String userId, Date startDate, Date endDate) {
        List<UserPerformance> performances = userPerformanceRepository.findByUserIdAndPerformanceDateBetween(userId,
                startDate, endDate).orElse(Collections.emptyList());

        Map<String, Double> result = new HashMap<>();
        if (performances.isEmpty()) {
            return result;
        }

        // Finding max and min time taken
        long maxTimeTaken = 0;
        long minTimeTaken = Long.MAX_VALUE;

        for (UserPerformance performance : performances) {
            long timeTaken = performance.getTimeTakenInSeconds();
            if (timeTaken > maxTimeTaken) {
                maxTimeTaken = timeTaken;
            }
            if (timeTaken < minTimeTaken) {
                minTimeTaken = timeTaken;
            }
        }

        result.put("maxTimeTaken", (double) maxTimeTaken);
        result.put("minTimeTaken", (double) minTimeTaken);

        return result;
    }

    public Map<String, Double> analyzeUserPerformance(String userId, Date startDate, Date endDate) {
        Map<String, Double> totalMissesResult = calculateTotalMissesForUser(userId, startDate, endDate);
        Map<String, Double> totalTimeTakenResult = calculateTotalTimeTakenForUser(userId, startDate, endDate);
        Map<String, Double> MaxMinMissesForUser = calculateMaxMinMissesForUser(userId, startDate, endDate);
        Map<String, Double> maxMinTimeResult = calculateMaxMinTimeTakenForUser(userId, startDate, endDate);
        Map<String, Double> result = new HashMap<>();
        result.putAll(totalMissesResult);
        result.putAll(totalTimeTakenResult);
        result.putAll(MaxMinMissesForUser);
        result.putAll(maxMinTimeResult);
        return result;
    }

    public Map<String, Double> getStatsByGame(String userId, String gameId) {
        List<UserPerformance> userPerformances = userPerformanceRepository.findByGameIdAndUserId(Long.parseLong(gameId), userId).orElse(Collections.emptyList());
        Map<String, Double> result = new HashMap<>();

        if (userPerformances.isEmpty()) {
            return result;
        }

        // Initialize variables for max and min values
        int maxMisses = Integer.MIN_VALUE;
        int minMisses = Integer.MAX_VALUE;

        long maxTimeTaken = Long.MIN_VALUE;
        long minTimeTaken = Long.MAX_VALUE;

        int totalMisses = 0;
        long totalTimeTaken = 0;

        // Iterate through user performances to calculate statistics
        for (UserPerformance performance : userPerformances) {
            int misses = performance.getMisses();
            long timeTaken = performance.getTimeTakenInSeconds();

            // Update total misses and total time taken
            totalMisses += misses;
            totalTimeTaken += timeTaken;

            // Update max and min misses
            if (misses > maxMisses) {
                maxMisses = misses;
            }
            if (misses < minMisses) {
                minMisses = misses;
            }

            // Update max and min time taken
            if (timeTaken > maxTimeTaken) {
                maxTimeTaken = timeTaken;
            }
            if (timeTaken < minTimeTaken) {
                minTimeTaken = timeTaken;
            }
        }

        // Calculate average misses and average time taken
        double averageMisses = (double) totalMisses / userPerformances.size();
        double averageTimeTaken = (double) totalTimeTaken / userPerformances.size();

        // Add statistics to the result map
        result.put("averageMisses", averageMisses);
        result.put("averageTimeTaken", averageTimeTaken);
        result.put("totalTimeTaken", (double) totalTimeTaken);
        result.put("maxMisses", (double) maxMisses);
        result.put("minMisses", (double) minMisses);
        result.put("maxTimeTaken", (double) maxTimeTaken);
        result.put("minTimeTaken", (double) minTimeTaken);

        return result;
    }

    // Analysis for game performance
    public Map<String, Double> analyzeGamePerformance(String gameId) {
        List<UserPerformance> gamePerformances = userPerformanceRepository.findByGameId(Long.parseLong(gameId)).orElse(Collections.emptyList());
        Map<String, Double> result = new HashMap<>();

        if (gamePerformances.isEmpty()) {
            return result;
        }

        // Calculate average misses for the game
        int totalMisses = 0;
        for (UserPerformance gameList : gamePerformances) {
            totalMisses += gameList.getMisses();
        }
        double averageMisses = (double) totalMisses / gamePerformances.size();
        result.put("averageMisses", averageMisses);

        // Calculate average time taken for the game

        long totalTimeTaken = 0;
        for (UserPerformance gameList : gamePerformances) {
            totalTimeTaken += gameList.getTimeTakenInSeconds();
        }
        double averageTimeTaken = (double) totalTimeTaken / gamePerformances.size();
        result.put("averageTimeTaken", averageTimeTaken);
        return result;
    }
}