package com.tmoreno.mooc.backoffice.review;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.shared.domain.CreatedOn;
import com.tmoreno.mooc.shared.domain.Entity;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;

public final class Review extends Entity<ReviewId> {

    private final CourseId courseId;
    private final StudentId studentId;
    private final ReviewRating rating;
    private final ReviewText text;
    private final CreatedOn createdOn;

    public Review(
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
