package com.tmoreno.mooc.backoffice.teacher.commands;

import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.domain.exceptions.teacher.TeacherNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.EventBus;

public final class UpdateTeacherCommand implements Command<UpdateTeacherCommandParams> {
    private final TeacherRepository repository;
    private final EventBus eventBus;

    public UpdateTeacherCommand(TeacherRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(UpdateTeacherCommandParams params) {
        TeacherId id = new TeacherId(params.getId());
        PersonName name = new PersonName(params.getName());
        Email email = new Email(params.getEmail());

        Teacher teacher = repository.find(id).orElseThrow(() -> new TeacherNotFoundException(id));

        teacher.changeName(name);
        teacher.changeEmail(email);

        repository.save(teacher);

        eventBus.publish(teacher.pullEvents());
    }
}
