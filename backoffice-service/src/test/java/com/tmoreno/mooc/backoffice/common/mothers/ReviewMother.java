package com.tmoreno.mooc.backoffice.common.mothers;

import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.shared.mothers.CreatedOnMother;

public final class ReviewMother {
    public static Review random() {
        return Review.restore(
            ReviewIdMother.random().getValue(),
            CourseIdMother.random().getValue(),
            StudentIdMother.random().getValue(),
            ReviewRatingMother.random().name(),
            ReviewTextMother.random().value(),
            CreatedOnMother.random().getValue()
        );
    }
}
