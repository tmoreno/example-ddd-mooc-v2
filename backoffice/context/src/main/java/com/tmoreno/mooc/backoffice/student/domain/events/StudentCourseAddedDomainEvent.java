package com.tmoreno.mooc.backoffice.student.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;

public final class StudentCourseAddedDomainEvent extends DomainEvent {
    private final CourseId courseId;

    public StudentCourseAddedDomainEvent(StudentId studentId, CourseId courseId) {
        super(studentId);

        this.courseId = courseId;
    }

    @Override
    public String getEventName() {
        return "student.course.added";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public CourseId getCourseId() {
        return courseId;
    }
}
