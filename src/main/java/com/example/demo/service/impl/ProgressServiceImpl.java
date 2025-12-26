package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.MicroLesson;
import com.example.demo.model.Progress;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProgressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;

    public ProgressServiceImpl(ProgressRepository progressRepository, UserRepository userRepository, 
                              MicroLessonRepository microLessonRepository) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.microLessonRepository = microLessonRepository;
    }

    @Override
    public Progress recordProgress(Long userId, Long lessonId, Progress progress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        MicroLesson lesson = microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
        
        if (progress.getProgressPercent() < 0 || progress.getProgressPercent() > 100) {
            throw new IllegalArgumentException("Progress percent must be between 0 and 100");
        }
        
        if ("COMPLETED".equals(progress.getStatus()) && progress.getProgressPercent() != 100) {
            throw new IllegalArgumentException("Completed status requires 100% progress");
        }
        
        Progress existing = progressRepository.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElse(null);
        
        if (existing != null) {
            existing.setStatus(progress.getStatus());
            existing.setProgressPercent(progress.getProgressPercent());
            existing.setScore(progress.getScore());
            return progressRepository.save(existing);
        } else {
            progress.setUser(user);
            progress.setMicroLesson(lesson);
            return progressRepository.save(progress);
        }
    }

    @Override
    public Progress getProgress(Long userId, Long lessonId) {
        return progressRepository.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Progress not found"));
    }

    @Override
    public List<Progress> getUserProgress(Long userId) {
        return progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}