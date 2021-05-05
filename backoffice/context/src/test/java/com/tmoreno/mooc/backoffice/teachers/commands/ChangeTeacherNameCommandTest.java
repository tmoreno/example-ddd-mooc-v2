package com.tmoreno.mooc.backoffice.teachers.commands;

import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teachers.commands.changeName.ChangeTeacherNameCommand;
import com.tmoreno.mooc.backoffice.teachers.commands.changeName.ChangeTeacherNameCommandParams;
import com.tmoreno.mooc.backoffice.teachers.domain.Teacher;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teachers.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
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
public class ChangeTeacherNameCommandTest {

    @Mock
    private TeacherRepository repository;

    private FakeEventBus eventBus;

    private ChangeTeacherNameCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new ChangeTeacherNameCommand(repository, eventBus);
    }

    @Test
    public void given_an_existing_teacher_when_change_name_then_name_is_changed_and_persisted_and_an_event_is_published() {
        Teacher teacher = TeacherMother.random();
        PersonName name = PersonNameMother.random();

        when(repository.find(teacher.getId())).thenReturn(Optional.of(teacher));

        ChangeTeacherNameCommandParams params = new ChangeTeacherNameCommandParams();
        params.setId(teacher.getId().getValue());
        params.setName(name.getValue());

        command.execute(params);

        assertThat(teacher.getName(), is(name));

        assertThat(eventBus.getEvents().size(), is(1));

        verify(repository).save(teacher);
    }

    @Test
    public void given_a_not_existing_teacher_when_change_name_then_throws_an_exception() {
        Assertions.assertThrows(TeacherNotFoundException.class, () -> {
            Teacher teacher = TeacherMother.random();
            PersonName name = PersonNameMother.random();

            when(repository.find(teacher.getId())).thenReturn(Optional.empty());

            ChangeTeacherNameCommandParams params = new ChangeTeacherNameCommandParams();
            params.setId(teacher.getId().getValue());
            params.setName(name.getValue());

            command.execute(params);
        });
    }
}
