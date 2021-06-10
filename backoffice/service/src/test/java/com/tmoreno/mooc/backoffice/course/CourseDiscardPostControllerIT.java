package com.tmoreno.mooc.backoffice.course;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseState;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseDiscardedDomainEvent;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertOk;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertPreconditionFailed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class CourseDiscardPostControllerIT extends BaseControllerIT {

    @SpyBean
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        url += "/courses/%s/discard";
    }

    @Test
    public void given_a_published_course_when_send_discard_request_then_receive_ok_response_and_course_is_discarded_and_event_is_stored() {
        Course course = CourseMother.randomInPublishState();
        courseRepository.save(course);

        url = String.format(url, course.getId().getValue());

        ResponseEntity<String> response = post();

        assertOk(response);

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getState(), is(CourseState.DISCARDED));

        verify(domainEventRepository).store(any(CourseDiscardedDomainEvent.class));
    }

    @Test
    public void given_a_discarded_course_when_send_discard_request_then_receive_precondition_failed_response() {
        Course course = CourseMother.randomInDiscardedState();
        courseRepository.save(course);

        url = String.format(url, course.getId().getValue());

        ResponseEntity<String> response = post();

        assertPreconditionFailed(response);
    }

    @Test
    public void given_not_existing_course_when_send_discard_request_then_receive_not_found_response() {
        url = String.format(url, CourseIdMother.random().getValue());

        ResponseEntity<String> response = post();

        assertNotFound(response);
    }

    @Test
    public void should_rollback_all_database_changes_when_an_exception_happen() {
        doThrow(RuntimeException.class).when(domainEventRepository).store(any());

        Course course = CourseMother.randomInPublishState();
        courseRepository.save(course);

        url = String.format(url, course.getId().getValue());

        post();

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getState(), is(CourseState.PUBLISHED));
    }
}