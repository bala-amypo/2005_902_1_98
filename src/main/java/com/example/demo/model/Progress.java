package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "micro_lesson_id", nullable = false)
    private MicroLesson microLesson;

    @Column(nullable = false)
    @Builder.Default
    private String status = "NOT_STARTED";

    @Column(nullable = false)
    @Builder.Default
    private Integer progressPercent = 0;

    @Column(nullable = false)
    private LocalDateTime lastAccessedAt;

    private BigDecimal score;

    @PrePersist
    protected void onCreate() {
        if (lastAccessedAt == null) {
            lastAccessedAt = LocalDateTime.now();
        }
        if (status == null) {
            status = "NOT_STARTED";
        }
        if (progressPercent == null) {
            progressPercent = 0;
        }
    }

    public void prePersist() {
        onCreate();
    }

    @PreUpdate
    protected void onUpdate() {
        lastAccessedAt = LocalDateTime.now();
    }
}