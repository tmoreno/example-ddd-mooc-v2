package com.tmoreno.mooc.backoffice.teachers.commands.changeName;

import com.tmoreno.mooc.backoffice.teachers.domain.Teacher;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teachers.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.EventBus;

public final class ChangeTeacherNameCommand implements Command<ChangeTeacherNameCommandParams> {
    private final TeacherRepository repository;
    private final EventBus eventBus;

    public ChangeTeacherNameCommand(TeacherRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(ChangeTeacherNameCommandParams params) {
        TeacherId id = new TeacherId(params.getId());
        PersonName name = new PersonName(params.getName());

        Teacher teacher = repository.find(id).orElseThrow(() -> new TeacherNotFoundException(id));
        
        teacher.changeName(name);

        repository.save(teacher);

        eventBus.publish(teacher.pullEvents());
    }
}
