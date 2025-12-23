package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private MicroLesson lesson;

    public Recommendation() {
        this.createdAt = LocalDateTime.now();
    }

    public Recommendation(String reason) {
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public MicroLesson getLesson() { return lesson; }

    public void setLesson(MicroLesson lesson) { this.lesson = lesson; }
}
