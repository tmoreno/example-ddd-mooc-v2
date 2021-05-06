package com.tmoreno.mooc.backoffice.teachers.handlers;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.events.CourseTeacherAddedDomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.Teacher;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teachers.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class CourseTeacherAddedDomainEventHandler implements EventHandler<CourseTeacherAddedDomainEvent> {

    private final TeacherRepository repository;

    public CourseTeacherAddedDomainEventHandler(TeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CourseTeacherAddedDomainEvent event) {
        CourseId courseId = new CourseId(event.getAggregateId());
        TeacherId teacherId = event.getTeacherId();

        Teacher teacher = repository.find(teacherId).orElseThrow(() -> new TeacherNotFoundException(teacherId));

        teacher.addCourse(courseId);

        repository.save(teacher);
    }
}
