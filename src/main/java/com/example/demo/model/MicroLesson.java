package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "micro_lessons")
public class MicroLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contentType; // VIDEO / QUIZ / ARTICLE

    private String difficulty; // BEGINNER / INTERMEDIATE / ADVANCED

    private String tags; // comma separated

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public MicroLesson() {}

    public MicroLesson(String title, String contentType, String difficulty, String tags) {
        this.title = title;
        this.contentType = contentType;
        this.difficulty = difficulty;
        this.tags = tags;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContentType() { return contentType; }

    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getDifficulty() { return difficulty; }

    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getTags() { return tags; }

    public void setTags(String tags) { this.tags = tags; }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }
}
