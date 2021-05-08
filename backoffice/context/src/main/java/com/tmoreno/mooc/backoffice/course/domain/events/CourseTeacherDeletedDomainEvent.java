package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;

public final class CourseTeacherDeletedDomainEvent extends DomainEvent {
    private final TeacherId teacherId;

    public CourseTeacherDeletedDomainEvent(CourseId courseId, TeacherId teacherId) {
        super(courseId);
        this.teacherId = teacherId;
    }

    @Override
    public String getEventName() {
        return "course.teacher.deleted";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public TeacherId getTeacherId() {
        return teacherId;
    }
}
