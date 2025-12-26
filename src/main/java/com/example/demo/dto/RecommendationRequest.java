package com.example.demo.dto;

import lombok.Data;

@Data
public class RecommendationRequest {
    private String targetDifficulty;
    private String tags;
    private Integer maxItems = 5;
    private String contentType;
}