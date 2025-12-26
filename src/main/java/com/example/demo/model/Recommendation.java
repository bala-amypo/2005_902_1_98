package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime generatedAt;

    @Column(nullable = false, length = 1000)
    private String recommendedLessonIds;

    @Column(length = 2000)
    private String basisSnapshot;

    @Column(nullable = false)
    private BigDecimal confidenceScore;

    @PrePersist
    protected void onCreate() {
        generatedAt = LocalDateTime.now();
    }
}