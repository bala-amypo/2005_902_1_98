package com.example.demo.repository;

import com.example.demo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByTitleAndInstructorId(String title, Long instructorId);
    List<Course> findByInstructorId(Long instructorId);
}