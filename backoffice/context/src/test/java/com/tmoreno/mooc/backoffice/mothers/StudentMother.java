package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class StudentMother {
    public static Student random() {
        return new Student(
            StudentIdMother.random(),
            PersonNameMother.random(),
            EmailMother.random()
        );
    }

    public static Student randomWithCourse(CourseId courseId) {
        Set<CourseId> courses = new HashSet<>();
        courses.add(courseId);

        return new Student(
            StudentIdMother.random(),
            PersonNameMother.random(),
            EmailMother.random(),
            courses,
            Map.of()
        );
    }

    public static Student randomWithReview(CourseId courseId, ReviewId reviewId) {
        Map<CourseId, ReviewId> reviews = new HashMap<>();
        reviews.put(courseId, reviewId);

        return new Student(
            StudentIdMother.random(),
            PersonNameMother.random(),
            EmailMother.random(),
            Set.of(),
            reviews
        );
    }
}
