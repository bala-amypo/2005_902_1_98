package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Create course for instructor
    @PostMapping("/{instructorId}")
    public Course createCourse(@PathVariable Long instructorId,
                               @RequestBody Course course) {
        return courseService.createCourse(course, instructorId);
    }

    // List courses by instructor
    @GetMapping("/instructor/{instructorId}")
    public List<Course> listCourses(@PathVariable Long instructorId) {
        return courseService.listByInstructor(instructorId);
    }
}
