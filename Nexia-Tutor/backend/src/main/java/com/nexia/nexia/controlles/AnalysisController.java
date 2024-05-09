package com.nexia.nexia.controlles;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexia.nexia.models.UserPerformance;
import com.nexia.nexia.repositories.UserPerformanceRepository;
import com.nexia.nexia.services.AnalysisService;
import com.nexia.nexia.services.GameService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private UserPerformanceRepository userPerformanceRepository;
    @Autowired
    private GameService gameService;

    @PostMapping("/user-performance/save")
    public ResponseEntity save(@RequestBody Map<String, String> body) {
        String dateString = body.get("enddate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Parse the date string to a Date object and save it directly in your method
            // call
            analysisService.save(
                    body.get("token"),
                    body.get("gameID"),
                    Short.parseShort(body.get("misses")),
                    Long.parseLong(body.get("time")),
                    dateFormat.parse(dateString) // Parse and convert the date string in one step
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error in saving data ", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/performance")
    public ResponseEntity analyzeUserPerformance(@PathVariable String userId,
            @RequestParam String startDate, @RequestParam String endDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedStartDate = dateFormat.parse(startDate);
            Date parsedEndDate = dateFormat.parse(endDate);
            Map<String, Double> analysisResult = analysisService.analyzeUserPerformance(userId, parsedStartDate,
                    parsedEndDate);
            if (analysisResult.isEmpty()) {
                return new ResponseEntity<>("No data found for the user with ID: " + userId, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("Result: " + analysisResult, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid date format", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}/game/{gameID}/performance")
    public ResponseEntity userPerformaneceForGame(@PathVariable String userId, @PathVariable String gameId) {
        Map<String, Double> analysisResult = analysisService.getStatsByGame(userId, gameId);
        if (analysisResult.isEmpty()) {
            return new ResponseEntity<>("No data found for the user with ID: " + userId, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Result: " + analysisResult, HttpStatus.OK);
        }
    }

    @GetMapping("/game/{gameId}/performance")
    public ResponseEntity gamePerformance(@PathVariable String gameId) {
        Map<String, Double> result = analysisService.analyzeGamePerformance(gameId);
        if (result.isEmpty()) {
            return new ResponseEntity<>("No data found for the game with ID: " + gameId, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Result: " + result, HttpStatus.OK);
    }

}