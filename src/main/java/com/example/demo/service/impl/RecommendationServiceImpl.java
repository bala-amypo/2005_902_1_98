package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final UserRepository userRepository;
    private final MicroLessonRepository lessonRepository;
    private final RecommendationRepository recommendationRepository;

    public RecommendationServiceImpl(UserRepository userRepository,
                                     MicroLessonRepository lessonRepository,
                                     RecommendationRepository recommendationRepository) {
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public Recommendation generate(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Simple recommendation logic: pick first lesson
        MicroLesson lesson = lessonRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No lessons available"));

        Recommendation recommendation = new Recommendation("Recommended based on interest");
        recommendation.setUser(user);
        recommendation.setLesson(lesson);

        return recommendationRepository.save(recommendation);
    }

    @Override
    public List<Recommendation> getUserRecommendations(Long userId) {
        return recommendationRepository.findByUserId(userId);
    }
}
