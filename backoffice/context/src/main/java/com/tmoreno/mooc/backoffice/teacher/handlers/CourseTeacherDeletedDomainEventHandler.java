package com.tmoreno.mooc.backoffice.teacher.handlers;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseTeacherDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class CourseTeacherDeletedDomainEventHandler implements EventHandler<CourseTeacherDeletedDomainEvent> {

    private final TeacherRepository repository;

    public CourseTeacherDeletedDomainEventHandler(TeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CourseTeacherDeletedDomainEvent event) {
        CourseId courseId = new CourseId(event.getAggregateId());
        TeacherId teacherId = event.getTeacherId();

        Teacher teacher = repository.find(teacherId).orElseThrow(() -> new TeacherNotFoundException(teacherId));

        teacher.deleteCourse(courseId);

        repository.save(teacher);
    }
}
