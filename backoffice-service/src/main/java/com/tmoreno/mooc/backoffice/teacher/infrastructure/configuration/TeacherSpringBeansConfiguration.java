package com.tmoreno.mooc.backoffice.teacher.infrastructure.configuration;

import com.tmoreno.mooc.backoffice.teacher.commands.CreateTeacherCommand;
import com.tmoreno.mooc.backoffice.teacher.commands.UpdateTeacherCommand;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teacher.handlers.CourseTeacherAddedDomainEventHandler;
import com.tmoreno.mooc.backoffice.teacher.handlers.CourseTeacherDeletedDomainEventHandler;
import com.tmoreno.mooc.backoffice.teacher.queries.FindTeacherQuery;
import com.tmoreno.mooc.backoffice.teacher.queries.FindTeachersQuery;
import com.tmoreno.mooc.shared.events.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeacherSpringBeansConfiguration {

    @Bean
    public CreateTeacherCommand createTeacherCommand(TeacherRepository repository, EventBus eventBus) {
        return new CreateTeacherCommand(repository, eventBus);
    }

    @Bean
    public UpdateTeacherCommand updateTeacherCommand(TeacherRepository repository, EventBus eventBus) {
        return new UpdateTeacherCommand(repository, eventBus);
    }

    @Bean
    public FindTeacherQuery findTeacherQuery(TeacherRepository repository) {
        return new FindTeacherQuery(repository);
    }

    @Bean
    public FindTeachersQuery findTeachersQuery(TeacherRepository repository) {
        return new FindTeachersQuery(repository);
    }

    @Bean
    public CourseTeacherAddedDomainEventHandler courseTeacherAddedDomainEventHandler(TeacherRepository repository) {
        return new CourseTeacherAddedDomainEventHandler(repository);
    }

    @Bean
    public CourseTeacherDeletedDomainEventHandler courseTeacherDeletedDomainEventHandler(TeacherRepository repository) {
        return new CourseTeacherDeletedDomainEventHandler(repository);
    }
}
