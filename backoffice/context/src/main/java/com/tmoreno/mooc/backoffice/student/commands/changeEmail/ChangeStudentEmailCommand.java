package com.tmoreno.mooc.backoffice.student.commands.changeEmail;

import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.events.EventBus;

public final class ChangeStudentEmailCommand implements Command<ChangeStudentEmailCommandParams> {
    private final StudentRepository repository;
    private final EventBus eventBus;

    public ChangeStudentEmailCommand(StudentRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(ChangeStudentEmailCommandParams params) {
        StudentId id = new StudentId(params.getId());
        Email email = new Email(params.getEmail());

        Student student = repository.find(id).orElseThrow(() -> new StudentNotFoundException(id));

        student.changeEmail(email);

        repository.save(student);

        eventBus.publish(student.pullEvents());
    }
}
