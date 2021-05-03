package com.tmoreno.mooc.backoffice.students.domain.exceptions;

import com.tmoreno.mooc.backoffice.courses.domain.ReviewId;
import com.tmoreno.mooc.backoffice.students.domain.StudentId;

public final class StudentReviewNotFoundException extends RuntimeException {
    public StudentReviewNotFoundException(StudentId studentId, ReviewId reviewId) {
        super("Review: " + reviewId + " not found in student: " + studentId);
    }
}
