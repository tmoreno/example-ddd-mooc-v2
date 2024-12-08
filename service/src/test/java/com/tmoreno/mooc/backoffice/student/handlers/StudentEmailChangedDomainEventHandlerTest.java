package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentEmailChangedDomainEvent;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.mothers.EmailMother;
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
public class StudentEmailChangedDomainEventHandlerTest {

    @Mock
    private StudentRepository repository;

    private StudentEmailChangedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new StudentEmailChangedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_student_have_been_email_changed_when_handle_the_event_then_student_email_is_changed() {
        Student student = StudentMother.random();
        Email email = EmailMother.random();

        when(repository.find(student.getId())).thenReturn(Optional.of(student));

        handler.handle(new StudentEmailChangedDomainEvent(student.getId(), email));

        assertThat(student.getEmail(), is(email));

        verify(repository).save(student);
    }
}
