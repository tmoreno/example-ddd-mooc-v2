package com.tmoreno.mooc.backoffice.teacher.configuration;

import com.tmoreno.mooc.backoffice.teacher.commands.create.CreateTeacherCommand;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.events.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeacherSpringBeansConfiguration {

    @Bean
    public CreateTeacherCommand createTeacherCommand(TeacherRepository repository, EventBus eventBus) {
        return new CreateTeacherCommand(repository, eventBus);
    }
}
