package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.MicroLesson;
import com.example.demo.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@Tag(name = "Lessons", description = "Micro-lesson management endpoints")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/course/{courseId}")
    @Operation(summary = "Add a lesson to a course")
    public ResponseEntity<ApiResponse<MicroLesson>> addLesson(@PathVariable Long courseId, 
                                                              @RequestBody MicroLesson lesson) {
        MicroLesson created = lessonService.addLesson(courseId, lesson);
        ApiResponse<MicroLesson> response = ApiResponse.<MicroLesson>builder()
                .success(true)
                .message("Lesson added successfully")
                .data(created)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{lessonId}")
    @Operation(summary = "Update a lesson")
    public ResponseEntity<ApiResponse<MicroLesson>> updateLesson(@PathVariable Long lessonId, 
                                                                 @RequestBody MicroLesson lesson) {
        MicroLesson updated = lessonService.updateLesson(lessonId, lesson);
        ApiResponse<MicroLesson> response = ApiResponse.<MicroLesson>builder()
                .success(true)
                .message("Lesson updated successfully")
                .data(updated)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search lessons by filters")
    public ResponseEntity<ApiResponse<List<MicroLesson>>> searchLessons(
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String contentType) {
        List<MicroLesson> lessons = lessonService.findLessonsByFilters(tags, difficulty, contentType);
        ApiResponse<List<MicroLesson>> response = ApiResponse.<List<MicroLesson>>builder()
                .success(true)
                .message("Lessons retrieved successfully")
                .data(lessons)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{lessonId}")
    @Operation(summary = "Get lesson by ID")
    public ResponseEntity<ApiResponse<MicroLesson>> getLesson(@PathVariable Long lessonId) {
        MicroLesson lesson = lessonService.getLesson(lessonId);
        ApiResponse<MicroLesson> response = ApiResponse.<MicroLesson>builder()
                .success(true)
                .message("Lesson retrieved successfully")
                .data(lesson)
                .build();
        return ResponseEntity.ok(response);
    }
}