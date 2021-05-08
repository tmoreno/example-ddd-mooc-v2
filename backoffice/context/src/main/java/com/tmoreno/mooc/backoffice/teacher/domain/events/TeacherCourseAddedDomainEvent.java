package com.tmoreno.mooc.backoffice.teacher.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;

public final class TeacherCourseAddedDomainEvent extends DomainEvent {
    private final CourseId courseId;

    public TeacherCourseAddedDomainEvent(TeacherId teacherId, CourseId courseId) {
        super(teacherId);

        this.courseId = courseId;
    }

    @Override
    public String getEventName() {
        return "teacher.course.added";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public CourseId getCourseId() {
        return courseId;
    }
}
