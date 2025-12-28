package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecommendationServiceImpl implements RecommendationService {
    
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;
    
    public RecommendationServiceImpl(RecommendationRepository recommendationRepository, 
                                   UserRepository userRepository, 
                                   MicroLessonRepository microLessonRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.microLessonRepository = microLessonRepository;
    }
    
    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest params) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<MicroLesson> lessons = microLessonRepository.findByFilters(
                params.getTags(), 
                params.getDifficulty(), 
                params.getContentType()
        );
        
        String recommendedIds = lessons.stream()
                .limit(params.getLimit() != null ? params.getLimit() : 5)
                .map(lesson -> lesson.getId().toString())
                .collect(Collectors.joining(","));
        
        String basisSnapshot = String.format("{\"tags\":\"%s\",\"difficulty\":\"%s\",\"contentType\":\"%s\"}", 
                params.getTags(), params.getDifficulty(), params.getContentType());
        
        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .recommendedLessonIds(recommendedIds)
                .basisSnapshot(basisSnapshot)
                .confidenceScore(BigDecimal.valueOf(0.85))
                .build();
        
        return recommendationRepository.save(recommendation);
    }
    
    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        List<Recommendation> recommendations = recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId);
        if (recommendations.isEmpty()) {
            throw new RuntimeException("No recommendations found");
        }
        return recommendations.get(0);
    }
    
    @Override
    public List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);
        return recommendationRepository.findByUserIdAndGeneratedAtBetween(userId, start, end);
    }
}