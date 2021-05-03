package com.tmoreno.mooc.backoffice.teachers.commands.changeEmail;

import com.tmoreno.mooc.backoffice.teachers.domain.Teacher;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teachers.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.events.EventBus;

public final class ChangeTeacherEmailCommand implements Command<ChangeTeacherEmailCommandParams> {
    private final TeacherRepository repository;
    private final EventBus eventBus;

    public ChangeTeacherEmailCommand(TeacherRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(ChangeTeacherEmailCommandParams params) {
        TeacherId id = new TeacherId(params.getId());
        Email email = new Email(params.getEmail());

        Teacher teacher = repository.find(id).orElseThrow(() -> new TeacherNotFoundException(id));
        
        teacher.changeEmail(email);

        repository.save(teacher);

        eventBus.publish(teacher.pullEvents());
    }
}
