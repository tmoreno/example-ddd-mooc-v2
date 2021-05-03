package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.courses.domain.Review;
import com.tmoreno.mooc.shared.mothers.CreatedOnMother;

public final class ReviewMother {
    public static Review random() {
        return new Review(
            ReviewIdMother.random(),
            StudentIdMother.random(),
            ReviewRatingMother.random(),
            ReviewTextMother.random(),
            CreatedOnMother.random()
        );
    }
}
