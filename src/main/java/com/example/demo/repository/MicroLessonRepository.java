package com.example.demo.repository;

import com.example.demo.model.MicroLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MicroLessonRepository extends JpaRepository<MicroLesson, Long> {

    List<MicroLesson> findByTagsContainingAndDifficultyAndContentType(
            String tags, String difficulty, String contentType);
}
