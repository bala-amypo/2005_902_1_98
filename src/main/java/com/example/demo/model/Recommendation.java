package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    private LocalDateTime generatedAt;
    
    @NotBlank
    @Column(length = 1000)
    private String recommendedLessonIds;
    
    @Column(length = 2000)
    private String basisSnapshot;
    
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    @Column(precision = 3, scale = 2)
    private BigDecimal confidenceScore;
    
    @PrePersist
    protected void onCreate() {
        this.generatedAt = LocalDateTime.now();
    }
    
    public void prePersist() {
        onCreate();
    }
}