package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public final class StudentMother {
    public static Student random() {
        return Student.restore(
            StudentIdMother.random().getValue(),
            PersonNameMother.random().getValue(),
            EmailMother.random().getValue(),
            Collections.emptySet(),
            Collections.emptyMap()
        );
    }

    public static Student randomWithCourse(CourseId courseId) {
        return Student.restore(
            StudentIdMother.random().getValue(),
            PersonNameMother.random().getValue(),
            EmailMother.random().getValue(),
            Set.of(courseId.getValue()),
            Collections.emptyMap()
        );
    }

    public static Student randomWithReview(CourseId courseId, ReviewId reviewId) {
        return Student.restore(
            StudentIdMother.random().getValue(),
            PersonNameMother.random().getValue(),
            EmailMother.random().getValue(),
            Collections.emptySet(),
            Map.of(courseId.getValue(), reviewId.getValue())
        );
    }

    public static Student randomWithCourseAndReview(CourseId courseId, ReviewId reviewId) {
        return Student.restore(
            StudentIdMother.random().getValue(),
            PersonNameMother.random().getValue(),
            EmailMother.random().getValue(),
            Set.of(courseId.getValue()),
            Map.of(courseId.getValue(), reviewId.getValue())
        );
    }
}
