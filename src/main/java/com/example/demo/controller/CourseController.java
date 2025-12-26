package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(name = "Courses", description = "Course management endpoints")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @Operation(summary = "Create a new course")
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody Course course, 
                                                            @RequestParam Long instructorId) {
        Course created = courseService.createCourse(course, instructorId);
        ApiResponse<Course> response = ApiResponse.<Course>builder()
                .success(true)
                .message("Course created successfully")
                .data(created)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "Update a course")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Long courseId, 
                                                            @RequestBody Course course) {
        Course updated = courseService.updateCourse(courseId, course);
        ApiResponse<Course> response = ApiResponse.<Course>builder()
                .success(true)
                .message("Course updated successfully")
                .data(updated)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/instructor/{instructorId}")
    @Operation(summary = "List courses by instructor")
    public ResponseEntity<ApiResponse<List<Course>>> listCoursesByInstructor(@PathVariable Long instructorId) {
        List<Course> courses = courseService.listCoursesByInstructor(instructorId);
        ApiResponse<List<Course>> response = ApiResponse.<List<Course>>builder()
                .success(true)
                .message("Courses retrieved successfully")
                .data(courses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "Get course by ID")
    public ResponseEntity<ApiResponse<Course>> getCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourse(courseId);
        ApiResponse<Course> response = ApiResponse.<Course>builder()
                .success(true)
                .message("Course retrieved successfully")
                .data(course)
                .build();
        return ResponseEntity.ok(response);
    }
}