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

    private final MicroLessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonServiceImpl(MicroLessonRepository lessonRepository,
                             CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public MicroLesson addLesson(Long courseId, MicroLesson lesson) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        lesson.setCourse(course);
        return lessonRepository.save(lesson);
    }

    @Override
    public List<MicroLesson> search(String tags, String difficulty, String contentType) {
        return lessonRepository
                .findByTagsContainingAndDifficultyAndContentType(
                        tags, difficulty, contentType);
    }
}
