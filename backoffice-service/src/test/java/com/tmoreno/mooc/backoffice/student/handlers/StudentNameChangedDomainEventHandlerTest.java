package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.common.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentNameChangedDomainEvent;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;
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
public class StudentNameChangedDomainEventHandlerTest {

    @Mock
    private StudentRepository repository;

    private StudentNameChangedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new StudentNameChangedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_student_have_been_name_changed_when_handle_the_event_then_student_name_is_changed() {
        Student student = StudentMother.random();
        PersonName name = PersonNameMother.random();

        when(repository.find(student.getId())).thenReturn(Optional.of(student));

        handler.handle(new StudentNameChangedDomainEvent(student.getId(), name));

        assertThat(student.getName(), is(name));

        verify(repository).save(student);
    }
}
