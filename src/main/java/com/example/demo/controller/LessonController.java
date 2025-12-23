package com.example.demo.controller;

import com.example.demo.model.MicroLesson;
import com.example.demo.service.LessonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    // Add lesson to a course
    @PostMapping("/course/{courseId}")
    public MicroLesson addLesson(@PathVariable Long courseId,
                                 @RequestBody MicroLesson lesson) {
        return lessonService.addLesson(courseId, lesson);
    }

    // Search lessons
    @GetMapping("/search")
    public List<MicroLesson> searchLessons(
            @RequestParam String tags,
            @RequestParam String difficulty,
            @RequestParam String contentType) {

        return lessonService.search(tags, difficulty, contentType);
    }
}
