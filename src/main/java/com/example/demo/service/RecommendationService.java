package com.example.demo.service;

import com.example.demo.model.Recommendation;
import java.util.List;

public interface RecommendationService {

    Recommendation generate(Long userId);

    List<Recommendation> getUserRecommendations(Long userId);
}
