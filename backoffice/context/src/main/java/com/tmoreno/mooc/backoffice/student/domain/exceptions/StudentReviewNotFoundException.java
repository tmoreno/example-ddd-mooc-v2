package com.tmoreno.mooc.backoffice.student.domain.exceptions;

import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class StudentReviewNotFoundException extends BaseDomainException {
    public StudentReviewNotFoundException(StudentId studentId, ReviewId reviewId) {
        super(
                "student-review-not-found",
                "Review: " + reviewId + " not found in student: " + studentId
        );
    }
}
