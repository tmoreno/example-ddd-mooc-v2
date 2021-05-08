package com.tmoreno.mooc.backoffice.student.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;

public final class StudentCourseDeletedDomainEvent extends DomainEvent {
    private final CourseId courseId;

    public StudentCourseDeletedDomainEvent(StudentId studentId, CourseId courseId) {
        super(studentId);

        this.courseId = courseId;
    }

    @Override
    public String getEventName() {
        return "student.course.deleted";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public CourseId getCourseId() {
        return courseId;
    }
}
