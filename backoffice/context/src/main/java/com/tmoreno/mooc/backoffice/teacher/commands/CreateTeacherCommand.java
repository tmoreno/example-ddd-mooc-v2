package com.tmoreno.mooc.backoffice.teacher.commands;

import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.domain.exceptions.teacher.TeacherExistsException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CreateTeacherCommand implements Command<CreateTeacherCommandParams> {
    private final TeacherRepository repository;
    private final EventBus eventBus;

    public CreateTeacherCommand(TeacherRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CreateTeacherCommandParams params) {
        TeacherId id = new TeacherId(params.getId());
        PersonName name = new PersonName(params.getName());
        Email email = new Email(params.getEmail());

        if (repository.exists(id, email)) {
            throw new TeacherExistsException(id, email);
        }

        Teacher teacher = Teacher.create(id, name, email);

        repository.save(teacher);

        eventBus.publish(teacher.pullEvents());
    }
}
