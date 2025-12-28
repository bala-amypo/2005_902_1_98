package com.example.demo.service.impl;

import com.example.demo.model.MicroLesson;
import com.example.demo.model.Progress;
import com.example.demo.model.User;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProgressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProgressServiceImpl implements ProgressService {
    
    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final MicroLessonRepository microLessonRepository;
    
    public ProgressServiceImpl(ProgressRepository progressRepository, 
                              UserRepository userRepository, 
                              MicroLessonRepository microLessonRepository) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.microLessonRepository = microLessonRepository;
    }
    
    @Override
    public Progress recordProgress(Long userId, Long lessonId, Progress progress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        MicroLesson lesson = microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        
        Optional<Progress> existing = progressRepository.findByUserIdAndMicroLessonId(userId, lessonId);
        
        Progress progressToSave;
        if (existing.isPresent()) {
            progressToSave = existing.get();
            progressToSave.setStatus(progress.getStatus());
            progressToSave.setProgressPercent(progress.getProgressPercent());
            progressToSave.setScore(progress.getScore());
            progressToSave.setLastAccessedAt(LocalDateTime.now());
            
            if ("COMPLETED".equals(progress.getStatus())) {
                progressToSave.setCompletedAt(LocalDateTime.now());
            }
        } else {
            progressToSave = Progress.builder()
                    .user(user)
                    .microLesson(lesson)
                    .status(progress.getStatus())
                    .progressPercent(progress.getProgressPercent())
                    .score(progress.getScore())
                    .lastAccessedAt(LocalDateTime.now())
                    .build();
            
            if ("COMPLETED".equals(progress.getStatus())) {
                progressToSave.setCompletedAt(LocalDateTime.now());
            }
        }
        
        return progressRepository.save(progressToSave);
    }
    
    @Override
    public Progress getProgress(Long userId, Long lessonId) {
        return progressRepository.findByUserIdAndMicroLessonId(userId, lessonId)
                .orElseThrow(() -> new RuntimeException("Progress not found"));
    }
    
    @Override
    public List<Progress> getUserProgress(Long userId) {
        return progressRepository.findByUserIdOrderByLastAccessedAtDesc(userId);
    }
}