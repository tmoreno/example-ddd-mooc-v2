package com.tmoreno.mooc.backoffice.course.commands.addTeacher;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.exceptions.teacher.TeacherNotFoundException;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CourseAddTeacherCommand implements Command<CourseAddTeacherCommandParams> {

    private final CourseRepository repository;
    private final TeacherRepository teacherRepository;
    private final EventBus eventBus;

    public CourseAddTeacherCommand(CourseRepository repository, TeacherRepository teacherRepository, EventBus eventBus) {
        this.repository = repository;
        this.teacherRepository = teacherRepository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseAddTeacherCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        TeacherId teacherId = new TeacherId(params.getTeacherId());

        if (!teacherRepository.exists(teacherId)) {
            throw new TeacherNotFoundException(teacherId);
        }

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.addTeacher(teacherId);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
