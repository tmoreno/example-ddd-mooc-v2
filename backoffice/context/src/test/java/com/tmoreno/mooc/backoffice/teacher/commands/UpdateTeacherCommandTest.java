package com.tmoreno.mooc.backoffice.teacher.commands;

import com.tmoreno.mooc.backoffice.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;
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
public class UpdateTeacherCommandTest {

    @Mock
    private TeacherRepository repository;

    private FakeEventBus eventBus;

    private UpdateTeacherCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new UpdateTeacherCommand(repository, eventBus);
    }

    @Test
    public void given_a_teacher_when_change_attribute_then_teacher_attribute_is_changed_and_events_are_published() {
        Teacher teacher = TeacherMother.random();
        Email email = EmailMother.random();
        PersonName name = PersonNameMother.random();

        when(repository.find(teacher.getId())).thenReturn(Optional.of(teacher));

        UpdateTeacherCommandParams params = new UpdateTeacherCommandParams();
        params.setId(teacher.getId().getValue());
        params.setEmail(email.getValue());
        params.setName(name.getValue());

        command.execute(params);

        assertThat(teacher.getEmail(), is(email));
        assertThat(teacher.getName(), is(name));

        verify(repository).save(teacher);

        assertThat(eventBus.getEvents().size(), is(2));
    }

    @Test
    public void given_a_teacher_when_changed_attribute_value_is_the_same_then_teacher_attribute_is_not_changed_and_events_is_not_published() {
        Teacher teacher = TeacherMother.random();
        Email email = teacher.getEmail();
        PersonName name = teacher.getName();

        when(repository.find(teacher.getId())).thenReturn(Optional.of(teacher));

        UpdateTeacherCommandParams params = new UpdateTeacherCommandParams();
        params.setId(teacher.getId().getValue());
        params.setEmail(email.getValue());
        params.setName(name.getValue());

        command.execute(params);

        assertThat(teacher.getEmail(), is(email));
        assertThat(teacher.getName(), is(name));

        verify(repository).save(teacher);

        assertThat(eventBus.getEvents().isEmpty(), is(true));
    }

    @Test
    public void given_a_not_existing_teacher_when_update_then_throws_an_exception() {
        Assertions.assertThrows(TeacherNotFoundException.class, () -> {
            UpdateTeacherCommandParams params = new UpdateTeacherCommandParams();
            params.setId(TeacherIdMother.random().getValue());
            params.setName(PersonNameMother.random().getValue());
            params.setEmail(EmailMother.random().getValue());

            command.execute(params);
        });
    }
}
