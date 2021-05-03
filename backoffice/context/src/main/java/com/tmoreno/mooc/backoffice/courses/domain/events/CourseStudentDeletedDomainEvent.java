package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.students.domain.StudentId;

public final class CourseStudentDeletedDomainEvent extends DomainEvent {
    private final StudentId studentId;

    public CourseStudentDeletedDomainEvent(CourseId courseId, StudentId studentId) {
        super(courseId);

        this.studentId = studentId;
    }

    @Override
    public String getEventName() {
        return "course.student.deleted";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public StudentId getStudentId() {
        return studentId;
    }
}
