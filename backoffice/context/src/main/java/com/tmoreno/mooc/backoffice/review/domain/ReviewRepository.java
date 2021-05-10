package com.tmoreno.mooc.backoffice.review.domain;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    void save(Review review);

    List<Review> findAll();

    Optional<Review> find(ReviewId id);

    List<Review> findByCourse(CourseId courseId);

    List<Review> findByStudent(StudentId studentId);
}
