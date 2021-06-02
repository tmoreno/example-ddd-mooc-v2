package com.tmoreno.mooc.backoffice.course;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.SectionTitle;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSectionAddedDomainEvent;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionTitleMother;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertCreated;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class CourseSectionPostControllerIT extends BaseControllerIT {

    @SpyBean
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        url += "/courses/%s/sections";
    }

    @Test
    public void given_a_course_without_sections_when_send_post_request_then_receive_created_response_and_section_is_persisted_and_event_is_stored() {
        Course course = CourseMother.randomInDraftState();

        courseRepository.save(course);

        SectionId sectionId = SectionIdMother.random();
        SectionTitle title = SectionTitleMother.random();

        url = String.format(url, course.getId().getValue());

        ResponseEntity<String> response = post(Map.of(
            "sectionId", sectionId.getValue(),
            "title", title.getValue()
        ));

        assertCreated(response);

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getSections().size(), is(1));
        assertThat(persistedCourse.getSections().get(0).getId(), is(sectionId));
        assertThat(persistedCourse.getSections().get(0).getTitle(), is(title));

        verify(domainEventRepository).store(any(CourseSectionAddedDomainEvent.class));
    }

    @Test
    public void given_not_existing_course_when_send_post_request_then_receive_not_found_response() {

        url = String.format(url, CourseIdMother.random().getValue());

        ResponseEntity<String> response = post(Map.of(
            "sectionId", SectionIdMother.random().getValue(),
            "title", SectionTitleMother.random().getValue()
        ));

        assertNotFound(response);
    }

    @Test
    public void should_rollback_all_database_changes_when_an_exception_happen() {
        doThrow(RuntimeException.class).when(domainEventRepository).store(any());

        Course course = CourseMother.randomInDraftState();

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue());

        post(Map.of(
            "sectionId", SectionIdMother.random().getValue(),
            "title", SectionTitleMother.random().getValue()
        ));

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getSections().isEmpty(), is(true));
    }
}
