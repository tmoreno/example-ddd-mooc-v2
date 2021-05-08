package com.tmoreno.mooc.backoffice.student.domain.exceptions;

import com.tmoreno.mooc.backoffice.review.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;

public final class StudentReviewNotFoundException extends RuntimeException {
    public StudentReviewNotFoundException(StudentId studentId, ReviewId reviewId) {
        super("Review: " + reviewId + " not found in student: " + studentId);
    }
}
