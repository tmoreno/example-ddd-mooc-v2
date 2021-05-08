package com.tmoreno.mooc.backoffice.teacher.commands;

import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.commands.changeEmail.ChangeTeacherEmailCommand;
import com.tmoreno.mooc.backoffice.teacher.commands.changeEmail.ChangeTeacherEmailCommandParams;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChangeTeacherEmailCommandTest {

    @Mock
    private TeacherRepository repository;

    private FakeEventBus eventBus;

    private ChangeTeacherEmailCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new ChangeTeacherEmailCommand(repository, eventBus);
    }

    @Test
    public void given_an_existing_teacher_when_change_email_then_email_is_changed_and_persisted_and_an_event_is_published() {
        Teacher teacher = TeacherMother.random();
        Email email = EmailMother.random();

        when(repository.find(teacher.getId())).thenReturn(Optional.of(teacher));

        ChangeTeacherEmailCommandParams params = new ChangeTeacherEmailCommandParams();
        params.setId(teacher.getId().getValue());
        params.setEmail(email.getValue());

        command.execute(params);

        assertThat(teacher.getEmail(), is(email));

        assertThat(eventBus.getEvents().size(), is(1));

        verify(repository).save(teacher);
    }

    @Test
    public void given_a_not_existing_teacher_when_change_email_then_throws_an_exception() {
        Assertions.assertThrows(TeacherNotFoundException.class, () -> {
            Teacher teacher = TeacherMother.random();
            Email email = EmailMother.random();

            when(repository.find(teacher.getId())).thenReturn(Optional.empty());

            ChangeTeacherEmailCommandParams params = new ChangeTeacherEmailCommandParams();
            params.setId(teacher.getId().getValue());
            params.setEmail(email.getValue());

            command.execute(params);
        });
    }
}
