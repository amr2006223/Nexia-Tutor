package com.nexia.nexia.services;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexia.nexia.models.UserPerformance;
import com.nexia.nexia.repositories.UserPerformanceRepository;

@Service
public class AnalysisService {
    @Autowired
    private UserPerformanceRepository userPerformanceRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;
    @Autowired
    private JwtService jwtService;

    public UserPerformance save(String token, String gameId, String misses, String time, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            UserPerformance userPerformance = new UserPerformance();
            userPerformance.setUser(userService.getEntityById(token));
            userPerformance.setGame(gameService.getEntityById(Long.parseLong(gameId)));// error
            userPerformance.setMisses(Short.parseShort(misses));
            userPerformance.setTimeTakenInSeconds(Long.parseLong(time));
            userPerformance.setPerformanceDate(dateFormat.parse(endDate));
            return userPerformanceRepository.save(userPerformance);
        } catch (Exception e) {
            return null;
        }
    }

    public int calculateTotalMissesForUser(List<UserPerformance> userPerformances, Date startDate, Date endDate) {
        int totalMisses = 0;
        for (UserPerformance userList : userPerformances) {
            totalMisses += userList.getMisses();
        }
        return totalMisses;
    }

    public long calculateTotalTimeTakenForUser(List<UserPerformance> userPerformances, Date startDate, Date endDate) {
        long totalTimeTaken = 0;
        for (UserPerformance userList : userPerformances) {
            totalTimeTaken += userList.getTimeTakenInSeconds();
        }
        return totalTimeTaken;
    }

    
    public Map<String, Double> calculateMaxMinTimeTakenForUser(
            List<UserPerformance> performances, Date startDate, Date endDate) {

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
        Map<String,Double> result = new HashMap<String,Double>();
        result.put("maxTimeTaken", (double) maxTimeTaken);
        result.put("minTimeTaken", (double) minTimeTaken);

        return result;
    }

    public Map<String, Double> analyzeUserPerformance(String userId, Date startDate, Date endDate) {
        userId = jwtService.extractUUID(userId);
        List<UserPerformance> userPerformances = userPerformanceRepository.findAllByUserIdAndPerformanceDateBetween(userId,
                startDate, endDate).orElse(Collections.emptyList());
        if(userPerformances == null) return null;
        double totalMissesResult = calculateTotalMissesForUser(userPerformances, startDate, endDate);
        double totalTimeTakenResult = calculateTotalTimeTakenForUser(userPerformances, startDate, endDate);
        Map<String, Double> maxMinTimeResult = calculateMaxMinTimeTakenForUser(userPerformances, startDate, endDate);

        Map<String, Double> result = new HashMap<>();

        result.put("Total Misses",totalMissesResult);
        result.put("Total Time Taken",totalTimeTakenResult);
        result.put("Max Time",maxMinTimeResult.get("maxTimeTaken"));
        result.put("Min Time", maxMinTimeResult.get("minTimeTaken"));
        return result;
    }

    public Map<String, Double> getStatsByGame(String userId, String gameId) {
        userId = jwtService.extractUUID(userId);
        List<UserPerformance> userPerformances = userPerformanceRepository
                .findAllByGameIdAndUserId(Long.parseLong(gameId), userId).orElse(Collections.emptyList());
        Map<String, Double> result = new HashMap<>();

        if (userPerformances.isEmpty()) {
            return null;
        }

        // Initialize variables for max and min values
        int maxMisses = 0;
        int minMisses = Integer.MAX_VALUE;

        long maxTimeTaken = 0;
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
        result.put("totalMisses",(double) totalMisses);
        result.put("maxTimeTaken", (double) maxTimeTaken);
        result.put("minTimeTaken", (double) minTimeTaken);

        return result;
    }

    // Analysis for game performance
    public Map<String, Double> analyzeGamePerformance(String gameId) {
        List<UserPerformance> gamePerformances = userPerformanceRepository.findAllByGameId(Long.parseLong(gameId))
                .orElse(Collections.emptyList());
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