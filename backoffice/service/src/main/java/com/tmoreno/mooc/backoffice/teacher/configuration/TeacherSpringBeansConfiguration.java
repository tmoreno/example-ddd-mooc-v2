package com.tmoreno.mooc.backoffice.teacher.configuration;

import com.tmoreno.mooc.backoffice.teacher.commands.UpdateTeacherCommand;
import com.tmoreno.mooc.backoffice.teacher.commands.changeEmail.ChangeTeacherEmailCommand;
import com.tmoreno.mooc.backoffice.teacher.commands.CreateTeacherCommand;
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

    @Bean
    public UpdateTeacherCommand updateTeacherCommand(TeacherRepository repository, EventBus eventBus) {
        return new UpdateTeacherCommand(repository, eventBus);
    }

    @Bean
    public ChangeTeacherEmailCommand changeTeacherEmailCommand(TeacherRepository repository, EventBus eventBus) {
        return new ChangeTeacherEmailCommand(repository, eventBus);
    }
}
