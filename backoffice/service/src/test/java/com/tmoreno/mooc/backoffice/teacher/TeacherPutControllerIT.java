package com.tmoreno.mooc.backoffice.teacher;

import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import com.tmoreno.mooc.backoffice.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TeacherPutControllerIT extends BaseControllerIT {

    @SpyBean
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {
        url += "/teachers";
    }

    @Test
    public void given_existing_teacher_when_send_put_request_then_receive_ok_response_and_teacher_is_changed_and_event_stored() {
        Teacher teacher = TeacherMother.random();

        teacherRepository.save(teacher);

        PersonName name = PersonNameMother.random();
        Email email = EmailMother.random();

        Map<String, Object> request = Map.of(
            "name", name.getValue(),
            "email", email.getValue()
        );

        ResponseEntity<String> response = put(teacher.getId().getValue(), request);

        assertOk(response);

        Teacher teacherPersisted = teacherRepository.find(teacher.getId()).orElseThrow();
        assertThat(teacherPersisted.getId(), is(teacher.getId()));
        assertThat(teacherPersisted.getName(), is(name));
        assertThat(teacherPersisted.getEmail(), is(email));

        verify(domainEventRepository, times(2)).store(any());
    }

    @Test
    public void given_not_existing_teacher_when_send_put_request_then_receive_not_found_response_and_teacher_is_not_changed_and_event_is_not_stored() {
        String teacherId = TeacherIdMother.random().getValue();

        Map<String, Object> request = Map.of(
            "name", PersonNameMother.random().getValue(),
            "email", EmailMother.random().getValue()
        );

        ResponseEntity<String> response = put(teacherId, request);

        assertNotFound(response);

        verify(teacherRepository, never()).save(any());

        verify(domainEventRepository, never()).store(any());
    }

    @Test
    public void should_rollback_all_database_changes_when_an_exception_happen() {
        Teacher teacher = TeacherMother.random();

        teacherRepository.save(teacher);

        doThrow(RuntimeException.class).when(domainEventRepository).store(any());

        put(
            teacher.getId().getValue(),
            Map.of(
                "name", PersonNameMother.random().getValue(),
                "email", EmailMother.random().getValue()
            )
        );

        Teacher persistedTeacher = teacherRepository.find(teacher.getId()).orElseThrow();
        assertThat(persistedTeacher.getId(), is(teacher.getId()));
        assertThat(persistedTeacher.getName(), is(teacher.getName()));
        assertThat(persistedTeacher.getEmail(), is(teacher.getEmail()));
    }
}
