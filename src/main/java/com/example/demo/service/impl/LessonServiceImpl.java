package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.MicroLesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.MicroLessonRepository;
import com.example.demo.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final MicroLessonRepository microLessonRepository;
    private final CourseRepository courseRepository;

    public LessonServiceImpl(MicroLessonRepository microLessonRepository, CourseRepository courseRepository) {
        this.microLessonRepository = microLessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        
        if (lesson.getDurationMinutes() == null) {
            lesson.setDurationMinutes(10); // Default duration
        }
        
        if (lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15) {
            throw new IllegalArgumentException("Duration must be between 1 and 15 minutes");
        }
        
        if (lesson.getContentType() == null) {
            lesson.setContentType("TEXT"); // Default content type
        }
        
        if (lesson.getDifficulty() == null) {
            lesson.setDifficulty("BEGINNER"); // Default difficulty
        }
        
        lesson.setCourse(course);
        return microLessonRepository.save(lesson);
    }

    @Override
    public MicroLesson updateLesson(Long lessonId, MicroLesson lesson) {
        MicroLesson existing = microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
        
        if (lesson.getTitle() != null) {
            existing.setTitle(lesson.getTitle());
        }
        if (lesson.getDurationMinutes() != null) {
            if (lesson.getDurationMinutes() <= 0 || lesson.getDurationMinutes() > 15) {
                throw new IllegalArgumentException("Duration must be between 1 and 15 minutes");
            }
            existing.setDurationMinutes(lesson.getDurationMinutes());
        }
        if (lesson.getContentType() != null) {
            existing.setContentType(lesson.getContentType());
        }
        if (lesson.getDifficulty() != null) {
            existing.setDifficulty(lesson.getDifficulty());
        }
        if (lesson.getTags() != null) {
            existing.setTags(lesson.getTags());
        }
        if (lesson.getPublishDate() != null) {
            existing.setPublishDate(lesson.getPublishDate());
        }
        
        return microLessonRepository.save(existing);
    }

    @Override
    public List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType) {
        return microLessonRepository.findByFilters(tags, difficulty, contentType);
    }

    @Override
    public MicroLesson getLesson(Long lessonId) {
        return microLessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }
}