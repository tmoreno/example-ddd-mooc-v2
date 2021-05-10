package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;

public final class CourseReviewNotFoundException extends RuntimeException {
    public CourseReviewNotFoundException(CourseId courseId, ReviewId reviewId) {
        super("Review: " + reviewId + " not found in course: " + courseId);
    }
}
