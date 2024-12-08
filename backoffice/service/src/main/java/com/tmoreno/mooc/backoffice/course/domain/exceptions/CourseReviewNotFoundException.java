package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class CourseReviewNotFoundException extends BaseDomainException {
    public CourseReviewNotFoundException(CourseId courseId, ReviewId reviewId) {
        super(
                "course-review-not-found",
                "Review: " + reviewId + " not found in course: " + courseId
        );
    }
}
