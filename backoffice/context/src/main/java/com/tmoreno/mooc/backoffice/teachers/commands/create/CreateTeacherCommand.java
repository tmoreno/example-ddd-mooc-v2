package com.tmoreno.mooc.backoffice.teachers.commands.create;

import com.tmoreno.mooc.backoffice.teachers.domain.Teacher;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherRepository;
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

        Teacher teacher = Teacher.create(id, name, email);

        repository.save(teacher);

        eventBus.publish(teacher.pullEvents());
    }
}
