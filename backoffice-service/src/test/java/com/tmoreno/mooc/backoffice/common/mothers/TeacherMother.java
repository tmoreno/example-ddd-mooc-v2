package com.tmoreno.mooc.backoffice.common.mothers;

import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;

import java.util.Set;

public final class TeacherMother {

    public static Teacher random() {
        return Teacher.restore(
            TeacherIdMother.random().getValue(),
            PersonNameMother.random().value(),
            EmailMother.random().value(),
            Set.of()
        );
    }

    public static Teacher randomWithCourse(CourseId courseId) {
        return Teacher.restore(
            TeacherIdMother.random().getValue(),
            PersonNameMother.random().value(),
            EmailMother.random().value(),
            Set.of(courseId.getValue())
        );
    }
}
