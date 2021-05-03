package com.tmoreno.mooc.backoffice.courses.domain;

import com.tmoreno.mooc.shared.utils.CreatedOnMother;
import com.tmoreno.mooc.backoffice.students.domain.StudentIdMother;

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
