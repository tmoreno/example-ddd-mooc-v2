package com.tmoreno.mooc.backoffice.student.commands.changeName;

import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.EventBus;

public final class ChangeStudentNameCommand implements Command<ChangeStudentNameCommandParams> {
    private final StudentRepository repository;
    private final EventBus eventBus;

    public ChangeStudentNameCommand(StudentRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(ChangeStudentNameCommandParams params) {
        StudentId id = new StudentId(params.getId());
        PersonName name = new PersonName(params.getName());

        Student student = repository.find(id).orElseThrow(() -> new StudentNotFoundException(id));
        
        student.changeName(name);

        repository.save(student);

        eventBus.publish(student.pullEvents());
    }
}
