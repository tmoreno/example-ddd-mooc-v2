package com.tmoreno.mooc.backoffice.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.CourseTitleMother;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertCreated;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertErrorCode;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertPreconditionFailed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CoursePostControllerIT extends BaseControllerIT {

    @SpyBean
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        url += "/courses";
    }

    @Test
    public void given_not_existing_course_when_send_post_request_then_receive_created_response_and_course_is_persisted_and_event_is_stored() {
        CourseId id = CourseIdMother.random();
        CourseTitle title = CourseTitleMother.random();

        ResponseEntity<String> response = post(Map.of(
            "id", id.getValue(),
            "title", title.getValue()
        ));

        assertCreated(response);

        Course persistedCourse = courseRepository.find(id).orElseThrow();
        assertThat(persistedCourse.getId(), is(id));
        assertThat(persistedCourse.getTitle(), is(title));

        verify(domainEventRepository).store(any(CourseCreatedDomainEvent.class));
    }

    @Test
    public void given_existing_id_course_when_send_post_request_then_receive_precondition_failed_response_and_course_is_not_persisted_and_event_is_not_stored() throws JsonProcessingException {
        Course course = CourseMother.random();

        courseRepository.save(course);

        ResponseEntity<String> response = post(Map.of(
            "id", course.getId().getValue(),
            "title", CourseTitleMother.random().getValue()
        ));

        assertPreconditionFailed(response);

        assertErrorCode(toJson(response.getBody()), "course-exists");

        verify(courseRepository, times(1)).save(any());

        verify(domainEventRepository, never()).store(any());
    }

    @Test
    public void given_existing_course_title_when_send_post_request_then_receive_precondition_failed_response_and_course_is_not_persisted_and_event_is_not_stored() throws JsonProcessingException {
        Course course = CourseMother.random();

        courseRepository.save(course);

        ResponseEntity<String> response = post(Map.of(
            "id", CourseIdMother.random().getValue(),
            "title", course.getTitle().getValue()
        ));

        assertPreconditionFailed(response);

        assertErrorCode(toJson(response.getBody()), "course-exists");

        verify(courseRepository, times(1)).save(any());

        verify(domainEventRepository, never()).store(any());
    }

    @Test
    public void should_rollback_all_database_changes_when_an_exception_happen() {
        doThrow(RuntimeException.class).when(domainEventRepository).store(any());

        CourseId id = CourseIdMother.random();
        CourseTitle title = CourseTitleMother.random();

        post(Map.of(
            "id", id.getValue(),
            "title", title.getValue()
        ));

        assertThat(courseRepository.find(id).isEmpty(), is(true));
    }
}
