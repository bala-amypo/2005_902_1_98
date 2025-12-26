package com.example.demo.service.impl;

import com.example.demo.dto.RecommendationRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Progress;
import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;
    private final ProgressRepository progressRepository;

    public RecommendationServiceImpl(RecommendationRepository recommendationRepository, 
                                   UserRepository userRepository,
                                   MicroLessonRepository microLessonRepository,
                                   ProgressRepository progressRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.microLessonRepository = microLessonRepository;
        this.progressRepository = progressRepository;
    }

    @Override
    public Recommendation generateRecommendation(Long userId, RecommendationRequest params) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        List<Progress> userProgress = progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);
        
        List<MicroLesson> candidateLessons = microLessonRepository.findByFilters(
                params.getTags(), params.getTargetDifficulty(), params.getContentType());
        
        // Filter out completed lessons
        List<Long> completedLessonIds = userProgress.stream()
                .filter(p -> "COMPLETED".equals(p.getStatus()))
                .map(p -> p.getMicroLesson().getId())
                .collect(Collectors.toList());
        
        List<MicroLesson> recommendedLessons = candidateLessons.stream()
                .filter(lesson -> !completedLessonIds.contains(lesson.getId()))
                .limit(params.getMaxItems() != null ? params.getMaxItems() : 5)
                .collect(Collectors.toList());
        
        String recommendedIds = recommendedLessons.stream()
                .map(lesson -> lesson.getId().toString())
                .collect(Collectors.joining(","));
        
        String basisSnapshot = String.format(
                "{\"completedLessons\":%d,\"filters\":{\"difficulty\":\"%s\",\"tags\":\"%s\",\"contentType\":\"%s\"}}",
                completedLessonIds.size(),
                params.getTargetDifficulty(),
                params.getTags(),
                params.getContentType()
        );
        
        BigDecimal confidence = BigDecimal.valueOf(recommendedLessons.isEmpty() ? 0.1 : 0.8);
        
        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .recommendedLessonIds(recommendedIds)
                .basisSnapshot(basisSnapshot)
                .confidenceScore(confidence)
                .build();
        
        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation getLatestRecommendation(Long userId) {
        List<Recommendation> recommendations = recommendationRepository.findByUserIdOrderByGeneratedAtDesc(userId);
        if (recommendations.isEmpty()) {
            throw new ResourceNotFoundException("No recommendations found");
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