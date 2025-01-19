package com.tmoreno.mooc.backoffice.course.commands.deleteTeacher;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CourseDeleteTeacherCommand implements Command<CourseDeleteTeacherCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public CourseDeleteTeacherCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseDeleteTeacherCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        TeacherId teacherId = new TeacherId(params.getTeacherId());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.deleteTeacher(teacherId);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
