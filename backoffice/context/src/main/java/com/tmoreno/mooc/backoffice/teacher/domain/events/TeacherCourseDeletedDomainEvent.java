package com.tmoreno.mooc.backoffice.teacher.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;

public final class TeacherCourseDeletedDomainEvent extends DomainEvent {
    private final CourseId courseId;

    public TeacherCourseDeletedDomainEvent(TeacherId teacherId, CourseId courseId) {
        super(teacherId);

        this.courseId = courseId;
    }

    @Override
    public String getEventName() {
        return "teacher.course.deleted";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public CourseId getCourseId() {
        return courseId;
    }
}
