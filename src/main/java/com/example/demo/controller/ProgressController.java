package com.example.demo.controller;

import com.example.demo.model.Progress;
import com.example.demo.service.ProgressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    // Record or update progress
    @PostMapping("/user/{userId}/lesson/{lessonId}")
    public Progress recordProgress(@PathVariable Long userId,
                                   @PathVariable Long lessonId,
                                   @RequestBody Progress progress) {
        return progressService.recordProgress(userId, lessonId, progress);
    }

    // Get progress for a user
    @GetMapping("/user/{userId}")
    public List<Progress> getUserProgress(@PathVariable Long userId) {
        return progressService.getUserProgress(userId);
    }
}
