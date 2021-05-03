package com.tmoreno.mooc.backoffice.courses.domain.exceptions;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.ReviewId;

public final class CourseReviewNotFoundException extends RuntimeException {
    public CourseReviewNotFoundException(CourseId courseId, ReviewId reviewId) {
        super("Review: " + reviewId + " not found in course: " + courseId);
    }
}
