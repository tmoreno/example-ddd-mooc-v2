package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.mothers.StudentIdMother;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentCreatedDomainEvent;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StudentCreatedDomainEventHandlerTest {

    @Mock
    private StudentRepository repository;

    private StudentCreatedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new StudentCreatedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_student_have_been_created_when_handle_the_event_then_student_is_created() {
        handler.handle(new StudentCreatedDomainEvent(
                StudentIdMother.random(),
                PersonNameMother.random(),
                EmailMother.random()
        ));

        verify(repository).save(any());
    }
}
