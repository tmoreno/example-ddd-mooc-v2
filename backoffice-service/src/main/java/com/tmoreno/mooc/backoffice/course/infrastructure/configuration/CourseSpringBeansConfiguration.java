package com.tmoreno.mooc.backoffice.course.infrastructure.configuration;

import com.tmoreno.mooc.backoffice.course.commands.addSection.CourseAddSectionCommand;
import com.tmoreno.mooc.backoffice.course.commands.addSectionClass.CourseAddSectionClassCommand;
import com.tmoreno.mooc.backoffice.course.commands.addTeacher.CourseAddTeacherCommand;
import com.tmoreno.mooc.backoffice.course.commands.updateCourse.UpdateCourseCommand;
import com.tmoreno.mooc.backoffice.course.commands.updateSection.UpdateCourseSectionCommand;
import com.tmoreno.mooc.backoffice.course.commands.updateSectionClass.UpdateCourseSectionClassCommand;
import com.tmoreno.mooc.backoffice.course.commands.create.CreateCourseCommand;
import com.tmoreno.mooc.backoffice.course.commands.deleteSection.CourseDeleteSectionCommand;
import com.tmoreno.mooc.backoffice.course.commands.deleteSectionClass.CourseDeleteSectionClassCommand;
import com.tmoreno.mooc.backoffice.course.commands.deleteTeacher.CourseDeleteTeacherCommand;
import com.tmoreno.mooc.backoffice.course.commands.discard.DiscardCourseCommand;
import com.tmoreno.mooc.backoffice.course.commands.publish.PublishCourseCommand;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.queries.FindCourseQuery;
import com.tmoreno.mooc.backoffice.course.queries.FindCoursesQuery;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.events.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourseSpringBeansConfiguration {

    @Bean
    public CreateCourseCommand createCourseCommand(CourseRepository repository, EventBus eventBus) {
        return new CreateCourseCommand(repository, eventBus);
    }

    @Bean
    public UpdateCourseCommand updateCourseCommand(CourseRepository repository, EventBus eventBus) {
        return new UpdateCourseCommand(repository, eventBus);
    }

    @Bean
    public PublishCourseCommand publishCourseCommand(CourseRepository repository, EventBus eventBus) {
        return new PublishCourseCommand(repository, eventBus);
    }

    @Bean
    public DiscardCourseCommand discardCourseCommand(CourseRepository repository, EventBus eventBus) {
        return new DiscardCourseCommand(repository, eventBus);
    }

    @Bean
    public CourseAddSectionCommand courseAddSectionCommand(CourseRepository repository, EventBus eventBus) {
        return new CourseAddSectionCommand(repository, eventBus);
    }

    @Bean
    public UpdateCourseSectionCommand updateCourseSectionCommand(CourseRepository repository, EventBus eventBus) {
        return new UpdateCourseSectionCommand(repository, eventBus);
    }

    @Bean
    public CourseDeleteSectionCommand courseDeleteSectionCommand(CourseRepository repository, EventBus eventBus) {
        return new CourseDeleteSectionCommand(repository, eventBus);
    }

    @Bean
    public CourseAddSectionClassCommand courseAddSectionClassCommand(CourseRepository repository, EventBus eventBus) {
        return new CourseAddSectionClassCommand(repository, eventBus);
    }

    @Bean
    public UpdateCourseSectionClassCommand updateCourseSectionClassCommand(CourseRepository repository, EventBus eventBus) {
        return new UpdateCourseSectionClassCommand(repository, eventBus);
    }

    @Bean
    public CourseDeleteSectionClassCommand CourseDeleteSectionClassCommand(CourseRepository repository, EventBus eventBus) {
        return new CourseDeleteSectionClassCommand(repository, eventBus);
    }

    @Bean
    public CourseAddTeacherCommand courseAddTeacherCommand(CourseRepository repository, TeacherRepository teacherRepository, EventBus eventBus) {
        return new CourseAddTeacherCommand(repository, teacherRepository, eventBus);
    }

    @Bean
    public CourseDeleteTeacherCommand courseDeleteTeacherCommand(CourseRepository repository, EventBus eventBus) {
        return new CourseDeleteTeacherCommand(repository, eventBus);
    }

    @Bean
    public FindCourseQuery findCourseQuery(CourseRepository repository) {
        return new FindCourseQuery(repository);
    }

    @Bean
    public FindCoursesQuery findCoursesQuery(CourseRepository repository) {
        return new FindCoursesQuery(repository);
    }
}
