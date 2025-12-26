package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Progress;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@Tag(name = "Progress", description = "Learning progress tracking endpoints")
public class ProgressController {

    private final ProgressService progressService;
    private final JwtUtil jwtUtil;

    public ProgressController(ProgressService progressService, JwtUtil jwtUtil) {
        this.progressService = progressService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/{lessonId}")
    @Operation(summary = "Record progress for a lesson")
    public ResponseEntity<ApiResponse<Progress>> recordProgress(@PathVariable Long lessonId, 
                                                                @RequestBody Progress progress,
                                                                Authentication auth) {
        // Extract user ID from JWT token (simplified for demo)
        Long userId = 1L; // In real implementation, extract from JWT claims
        
        Progress recorded = progressService.recordProgress(userId, lessonId, progress);
        ApiResponse<Progress> response = ApiResponse.<Progress>builder()
                .success(true)
                .message("Progress recorded successfully")
                .data(recorded)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/lesson/{lessonId}")
    @Operation(summary = "Get progress for a lesson")
    public ResponseEntity<ApiResponse<Progress>> getProgress(@PathVariable Long lessonId,
                                                             Authentication auth) {
        Long userId = 1L; // Extract from JWT
        
        Progress progress = progressService.getProgress(userId, lessonId);
        ApiResponse<Progress> response = ApiResponse.<Progress>builder()
                .success(true)
                .message("Progress retrieved successfully")
                .data(progress)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all progress for a user")
    public ResponseEntity<ApiResponse<List<Progress>>> getUserProgress(@PathVariable Long userId) {
        List<Progress> progressList = progressService.getUserProgress(userId);
        ApiResponse<List<Progress>> response = ApiResponse.<List<Progress>>builder()
                .success(true)
                .message("User progress retrieved successfully")
                .data(progressList)
                .build();
        return ResponseEntity.ok(response);
    }
}