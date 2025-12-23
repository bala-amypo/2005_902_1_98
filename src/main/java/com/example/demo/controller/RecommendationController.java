package com.example.demo.controller;

import com.example.demo.model.Recommendation;
import com.example.demo.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    // Generate recommendation for user
    @PostMapping("/{userId}")
    public Recommendation generate(@PathVariable Long userId) {
        return recommendationService.generate(userId);
    }

    // Get all recommendations for user
    @GetMapping("/user/{userId}")
    public List<Recommendation> getUserRecommendations(@PathVariable Long userId) {
        return recommendationService.getUserRecommendations(userId);
    }
}
