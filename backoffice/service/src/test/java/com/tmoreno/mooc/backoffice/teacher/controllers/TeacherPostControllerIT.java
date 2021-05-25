package com.tmoreno.mooc.backoffice.teacher.controllers;

import com.tmoreno.mooc.backoffice.BaseControllerIT;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teacher.domain.events.TeacherCreatedDomainEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TeacherPostControllerIT extends BaseControllerIT {

    @Autowired
    private TeacherRepository teacherRepository;

    private String url;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        url = "http://localhost:" + serverPort + "/teachers";
    }

    @Test
    public void given_not_existing_teacher_when_send_post_request_then_receive_created_response_and_teacher_is_persisted_and_event_is_stored() {
        Teacher teacher = TeacherMother.random();

        Map<String, Object> request = Map.of(
            "id", teacher.getId().getValue(),
            "name", teacher.getName().getValue(),
            "email", teacher.getEmail().getValue()
        );

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.hasBody(), is(false));

        Teacher teacherPersisted = teacherRepository.find(teacher.getId()).orElseThrow();
        assertThat(teacherPersisted.getId(), is(teacher.getId()));
        assertThat(teacherPersisted.getName(), is(teacher.getName()));
        assertThat(teacherPersisted.getEmail(), is(teacher.getEmail()));

        verifyDomainEventStored(TeacherCreatedDomainEvent.class);
    }
}
