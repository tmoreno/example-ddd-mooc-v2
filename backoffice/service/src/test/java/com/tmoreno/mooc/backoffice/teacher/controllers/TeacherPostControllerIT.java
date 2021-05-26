package com.tmoreno.mooc.backoffice.teacher.controllers;

import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import com.tmoreno.mooc.backoffice.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teacher.domain.events.TeacherCreatedDomainEvent;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertCreated;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertPreconditionFailed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TeacherPostControllerIT extends BaseControllerIT {

    @SpyBean
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {
        url += "/teachers";
    }

    @Test
    public void given_not_existing_teacher_when_send_post_request_then_receive_created_response_and_teacher_is_persisted_and_event_is_stored() {
        Teacher teacher = TeacherMother.random();

        Map<String, Object> request = Map.of(
            "id", teacher.getId().getValue(),
            "name", teacher.getName().getValue(),
            "email", teacher.getEmail().getValue()
        );

        ResponseEntity<String> response = post(request);

        assertCreated(response);

        Teacher teacherPersisted = teacherRepository.find(teacher.getId()).orElseThrow();
        assertThat(teacherPersisted.getId(), is(teacher.getId()));
        assertThat(teacherPersisted.getName(), is(teacher.getName()));
        assertThat(teacherPersisted.getEmail(), is(teacher.getEmail()));

        verify(domainEventRepository).store(any(TeacherCreatedDomainEvent.class));
    }

    @Test
    public void given_existing_id_teacher_when_send_post_request_then_receive_precondition_failed_response_and_teacher_is_not_persisted_and_event_is_not_stored() {
        Teacher teacher = TeacherMother.random();

        teacherRepository.save(teacher);

        Map<String, Object> request = Map.of(
            "id", teacher.getId().getValue(),
            "name", PersonNameMother.random().getValue(),
            "email", EmailMother.random().getValue()
        );

        ResponseEntity<String> response = post(request);

        assertPreconditionFailed(response);

        verify(teacherRepository, times(1)).save(any());

        verify(domainEventRepository, never()).store(any());
    }

    @Test
    public void given_existing_email_teacher_when_send_post_request_then_receive_precondition_failed_response_and_teacher_is_not_persisted_and_event_is_not_stored() {
        Teacher teacher = TeacherMother.random();

        teacherRepository.save(teacher);

        Map<String, Object> request = Map.of(
            "id", TeacherIdMother.random().getValue(),
            "name", PersonNameMother.random().getValue(),
            "email", teacher.getEmail().getValue()
        );

        ResponseEntity<String> response = post(request);

        assertPreconditionFailed(response);

        verify(teacherRepository, times(1)).save(any());

        verify(domainEventRepository, never()).store(any());
    }
}
