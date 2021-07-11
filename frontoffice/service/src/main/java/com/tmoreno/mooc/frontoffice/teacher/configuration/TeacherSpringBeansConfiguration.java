package com.tmoreno.mooc.frontoffice.teacher.configuration;

import com.tmoreno.mooc.frontoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.frontoffice.teacher.handlers.TeacherCreatedDomainEventHandler;
import com.tmoreno.mooc.frontoffice.teacher.handlers.TeacherEmailChangedDomainEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeacherSpringBeansConfiguration {

    @Bean
    public TeacherCreatedDomainEventHandler teacherCreatedDomainEventHandler(TeacherRepository repository) {
        return new TeacherCreatedDomainEventHandler(repository);
    }

    @Bean
    public TeacherEmailChangedDomainEventHandler teacherEmailChangedDomainEventHandler(TeacherRepository repository) {
        return new TeacherEmailChangedDomainEventHandler(repository);
    }
}
