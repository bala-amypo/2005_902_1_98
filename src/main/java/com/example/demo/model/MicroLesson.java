package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "micro_lessons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MicroLesson {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @NotBlank
    @Column(length = 150)
    private String title;
    
    @NotNull
    @Positive
    private Integer durationMinutes;
    
    @NotBlank
    @Column(length = 50)
    private String contentType;
    
    @NotBlank
    @Column(length = 50)
    private String difficulty;
    
    @Column(length = 500)
    private String tags;
    
    @NotNull
    private LocalDate publishDate;
}