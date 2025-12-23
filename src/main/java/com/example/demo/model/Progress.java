package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int completionPercentage;

    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private MicroLesson lesson;

    public Progress() {}

    public Progress(int completionPercentage, boolean completed) {
        this.completionPercentage = completionPercentage;
        this.completed = completed;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getCompletionPercentage() { return completionPercentage; }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public MicroLesson getLesson() { return lesson; }

    public void setLesson(MicroLesson lesson) { this.lesson = lesson; }
}
