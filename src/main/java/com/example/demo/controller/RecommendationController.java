package com.example.demo.controller;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.Recommendation;
import com.example.demo.service.RecommendationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
@Tag(name = "Recommendations")
public class RecommendationController {
    
    private final RecommendationService recommendationService;
    
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }
    
    @PostMapping("/generate")
    public ResponseEntity<Recommendation> generateRecommendation(@RequestParam Long userId, 
                                                               @RequestBody RecommendationRequest request) {
        Recommendation recommendation = recommendationService.generateRecommendation(userId, request);
        return ResponseEntity.ok(recommendation);
    }
    
    @GetMapping("/latest")
    public ResponseEntity<Recommendation> getLatestRecommendation(@RequestParam Long userId) {
        Recommendation recommendation = recommendationService.getLatestRecommendation(userId);
        return ResponseEntity.ok(recommendation);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getRecommendations(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<Recommendation> recommendations = recommendationService.getRecommendations(userId, from, to);
        return ResponseEntity.ok(recommendations);
    }
}