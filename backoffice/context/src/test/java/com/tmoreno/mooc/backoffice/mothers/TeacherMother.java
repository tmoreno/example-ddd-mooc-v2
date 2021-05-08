package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;

import java.util.HashSet;
import java.util.Set;

public final class TeacherMother {

    public static Teacher random() {
        return new Teacher(
            TeacherIdMother.random(),
            PersonNameMother.random(),
            EmailMother.random()
        );
    }

    public static Teacher randomWithCourse(CourseId courseId) {
        Set<CourseId> courses = new HashSet<>();
        courses.add(courseId);

        return new Teacher(
            TeacherIdMother.random(),
            PersonNameMother.random(),
            EmailMother.random(),
            courses
        );
    }
}
