package com.tmoreno.mooc.backoffice.review.domain;

import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.CreatedOn;
import com.tmoreno.mooc.shared.domain.Entity;

import java.time.Instant;

public final class Review extends Entity<ReviewId> {

    private final CourseId courseId;
    private final StudentId studentId;
    private final ReviewRating rating;
    private final ReviewText text;
    private final CreatedOn createdOn;

    private Review(
        ReviewId id,
        CourseId courseId,
        StudentId studentId,
        ReviewRating rating,
        ReviewText text,
        CreatedOn createdOn
    ) {
        super(id);

        this.courseId = courseId;
        this.studentId = studentId;
        this.rating = rating;
        this.text = text;
        this.createdOn = createdOn;
    }

    public static Review restore(
        String id,
        String courseId,
        String studentId,
        String rating,
        String text,
        long createdOn
    ) {
        return new Review(
            new ReviewId(id),
            new CourseId(courseId),
            new StudentId(studentId),
            ReviewRating.valueOf(rating),
            new ReviewText(text),
            new CreatedOn(Instant.ofEpochMilli(createdOn))
        );
    }

    public CourseId getCourseId() {
        return courseId;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public ReviewRating getRating() {
        return rating;
    }

    public ReviewText getText() {
        return text;
    }

    public CreatedOn getCreatedOn() {
        return createdOn;
    }
}
