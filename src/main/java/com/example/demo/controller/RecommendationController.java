package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.Recommendation;
import com.example.demo.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
@Tag(name = "Recommendations", description = "Content recommendation endpoints")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate recommendations for current user")
    public ResponseEntity<ApiResponse<Recommendation>> generateRecommendation(
            @RequestBody RecommendationRequest request,
            Authentication auth) {
        Long userId = 1L; // Extract from JWT
        
        Recommendation recommendation = recommendationService.generateRecommendation(userId, request);
        ApiResponse<Recommendation> response = ApiResponse.<Recommendation>builder()
                .success(true)
                .message("Recommendation generated successfully")
                .data(recommendation)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest")
    @Operation(summary = "Get latest recommendation for current user")
    public ResponseEntity<ApiResponse<Recommendation>> getLatestRecommendation(Authentication auth) {
        Long userId = 1L; // Extract from JWT
        
        Recommendation recommendation = recommendationService.getLatestRecommendation(userId);
        ApiResponse<Recommendation> response = ApiResponse.<Recommendation>builder()
                .success(true)
                .message("Latest recommendation retrieved successfully")
                .data(recommendation)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get recommendations for a user within date range")
    public ResponseEntity<ApiResponse<List<Recommendation>>> getRecommendations(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<Recommendation> recommendations = recommendationService.getRecommendations(userId, from, to);
        ApiResponse<List<Recommendation>> response = ApiResponse.<List<Recommendation>>builder()
                .success(true)
                .message("Recommendations retrieved successfully")
                .data(recommendations)
                .build();
        return ResponseEntity.ok(response);
    }
}