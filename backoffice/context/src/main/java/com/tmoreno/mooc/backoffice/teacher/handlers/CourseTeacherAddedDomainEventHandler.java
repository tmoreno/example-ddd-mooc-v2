package com.tmoreno.mooc.backoffice.teacher.handlers;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseTeacherAddedDomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class CourseTeacherAddedDomainEventHandler implements EventHandler<CourseTeacherAddedDomainEvent> {

    private final TeacherRepository repository;

    public CourseTeacherAddedDomainEventHandler(TeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CourseTeacherAddedDomainEvent event) {
        CourseId courseId = new CourseId(event.getCourseId());
        TeacherId teacherId = new TeacherId(event.getTeacherId());

        Teacher teacher = repository.find(teacherId).orElseThrow(() -> new TeacherNotFoundException(teacherId));

        teacher.addCourse(courseId);

        repository.save(teacher);
    }
}
