package com.nexia.nexia.controlles;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexia.nexia.services.AnalysisService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/nexia-tutor/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Map<String, String> body) {
        if (analysisService.save(
                body.get("token"),
                body.get("gameID"),
                body.get("misses"),
                body.get("time"),
                body.get("enddate")) == null)
            return new ResponseEntity<String>("Failed to add entity", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> analyzeUserPerformance(@RequestBody Map<String, String> body) {
        try {
            String userId = body.get("userId");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedStartDate = dateFormat.parse(body.get("startDate"));
            Date parsedEndDate = dateFormat.parse(body.get("endDate"));
            Map<String, Double> analysisResult = analysisService.analyzeUserPerformance(userId, parsedStartDate,
                    parsedEndDate);
            if (analysisResult == null) {
                return new ResponseEntity<String>("No data found for the user with ID: " + userId,
                        HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Map<String, Double>>(analysisResult, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Invalid date format", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}/{gameId}")
    public ResponseEntity<?> userPerformaneceForGame(@PathVariable String userId, @PathVariable String gameId) {
        Map<String, Double> analysisResult = analysisService.getStatsByGame(userId, gameId);
        if (analysisResult == null)
            return new ResponseEntity<String>("No Records Found", HttpStatus.OK);
        ;
        return new ResponseEntity<Map<String, Double>>(analysisResult, HttpStatus.OK);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<String> gamePerformance(@PathVariable String gameId) {
        Map<String, Double> result = analysisService.analyzeGamePerformance(gameId);
        if (result.isEmpty()) {
            return new ResponseEntity<>("No data found for the game with ID: " + gameId, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Result: " + result, HttpStatus.OK);
    }

}