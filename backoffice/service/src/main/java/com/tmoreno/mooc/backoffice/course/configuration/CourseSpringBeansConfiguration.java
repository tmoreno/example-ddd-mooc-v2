package com.tmoreno.mooc.backoffice.course.configuration;

import com.tmoreno.mooc.backoffice.course.commands.addSection.CourseAddSectionCommand;
import com.tmoreno.mooc.backoffice.course.commands.addSectionClass.CourseAddSectionClassCommand;
import com.tmoreno.mooc.backoffice.course.commands.addTeacher.CourseAddTeacherCommand;
import com.tmoreno.mooc.backoffice.course.commands.create.CreateCourseCommand;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.queries.FindCourseQuery;
import com.tmoreno.mooc.backoffice.course.queries.FindCoursesQuery;
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
    public CourseAddSectionCommand courseAddSectionCommand(CourseRepository repository, EventBus eventBus) {
        return new CourseAddSectionCommand(repository, eventBus);
    }

    @Bean
    public CourseAddSectionClassCommand courseAddSectionClassCommand(CourseRepository repository, EventBus eventBus) {
        return new CourseAddSectionClassCommand(repository, eventBus);
    }

    @Bean
    public CourseAddTeacherCommand courseAddTeacherCommand(CourseRepository repository, EventBus eventBus) {
        return new CourseAddTeacherCommand(repository, eventBus);
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
