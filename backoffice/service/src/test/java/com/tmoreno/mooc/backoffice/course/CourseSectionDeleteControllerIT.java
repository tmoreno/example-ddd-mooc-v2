package com.tmoreno.mooc.backoffice.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSectionDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionMother;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertErrorCode;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class CourseSectionDeleteControllerIT extends BaseControllerIT {

    @SpyBean
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        url += "/courses/%s/sections/%s";
    }

    @Test
    public void given_a_course_with_sections_when_send_delete_request_then_receive_ok_response_and_section_is_deleted_and_event_is_stored() {
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), section.getId().getValue());

        ResponseEntity<String> response = delete();

        assertOk(response);

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getSections().isEmpty(), is(true));

        verify(domainEventRepository).store(any(CourseSectionDeletedDomainEvent.class));
    }

    @Test
    public void given_not_existing_course_when_send_delete_request_then_receive_not_found_response() throws JsonProcessingException {

        url = String.format(url, CourseIdMother.random().getValue(), SectionIdMother.random().getValue());

        ResponseEntity<String> response = delete();

        assertNotFound(response);

        assertErrorCode(toJson(response.getBody()), "course-not-found");
    }

    @Test
    public void given_course_when_send_delete_request_for_a_not_existing_section_then_receive_not_found_response() throws JsonProcessingException {
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), SectionIdMother.random().getValue());

        ResponseEntity<String> response = delete();

        assertNotFound(response);

        assertErrorCode(toJson(response.getBody()), "course-section-not-found");
    }

    @Test
    public void given_course_without_sections_when_send_delete_request_then_receive_not_found_response() throws JsonProcessingException {
        Course course = CourseMother.randomInDraftState();

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), SectionIdMother.random().getValue());

        ResponseEntity<String> response = delete();

        assertNotFound(response);

        assertErrorCode(toJson(response.getBody()), "course-section-not-found");
    }

    @Test
    public void should_rollback_all_database_changes_when_an_exception_happen() {
        doThrow(RuntimeException.class).when(domainEventRepository).store(any());

        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), section.getId().getValue());

        delete();

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getSections().isEmpty(), is(false));
    }
}
