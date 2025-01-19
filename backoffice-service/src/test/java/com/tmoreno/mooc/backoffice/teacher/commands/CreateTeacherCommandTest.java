package com.tmoreno.mooc.backoffice.teacher.commands;

import com.tmoreno.mooc.backoffice.common.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherExistsException;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTeacherCommandTest {

    @Mock
    private TeacherRepository repository;

    private FakeEventBus eventBus;

    private CreateTeacherCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CreateTeacherCommand(repository, eventBus);
    }

    @Test
    public void given_a_not_existing_teacher_when_create_teacher_then_teacher_is_created_and_persisted_and_an_event_is_published() {
        CreateTeacherCommandParams params = new CreateTeacherCommandParams();
        params.setId(TeacherIdMother.random().getValue());
        params.setName(PersonNameMother.random().value());
        params.setEmail(EmailMother.random().value());

        command.execute(params);

        verify(repository).save(any());

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_existing_teacher_by_id_when_create_teacher_then_throws_an_exception() {
        assertThrows(TeacherExistsException.class, () -> {
            TeacherId teacherId = TeacherIdMother.random();
            Email email = EmailMother.random();

            when(repository.exists(teacherId, email)).thenReturn(true);

            CreateTeacherCommandParams params = new CreateTeacherCommandParams();
            params.setId(teacherId.getValue());
            params.setName(PersonNameMother.random().value());
            params.setEmail(email.value());

            command.execute(params);
        });
    }
}
