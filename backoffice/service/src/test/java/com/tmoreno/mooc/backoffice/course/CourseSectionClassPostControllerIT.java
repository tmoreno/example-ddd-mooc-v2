package com.tmoreno.mooc.backoffice.course;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassTitle;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSectionClassAddedDomainEvent;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.SectionClassIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionClassTitleMother;
import com.tmoreno.mooc.backoffice.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionTitleMother;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;
import com.tmoreno.mooc.shared.mothers.DurationInSecondsMother;
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

public class CourseSectionClassPostControllerIT extends BaseControllerIT {

    @SpyBean
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        url += "/courses/%s/sections/%s/classes";
    }

    @Test
    public void given_a_course_with_section_but_without_classes_when_send_post_request_then_receive_created_response_and_section_class_is_persisted_and_event_is_stored() {
        Course course = CourseMother.randomInDraftState();

        SectionId sectionId = SectionIdMother.random();
        course.addSection(sectionId, SectionTitleMother.random());

        courseRepository.save(course);

        SectionClassId sectionClassId = SectionClassIdMother.random();
        SectionClassTitle title = SectionClassTitleMother.random();
        DurationInSeconds duration = DurationInSecondsMother.random();

        url = String.format(url, course.getId().getValue(), sectionId.getValue());

        ResponseEntity<String> response = post(Map.of(
            "sectionClassId", sectionClassId.getValue(),
            "title", title.getValue(),
            "duration", duration.getValue()
        ));

        assertCreated(response);

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getSections().get(0).getClasses().size(), is(1));
        assertThat(persistedCourse.getSections().get(0).getClasses().get(0).getId(), is(sectionClassId));
        assertThat(persistedCourse.getSections().get(0).getClasses().get(0).getTitle(), is(title));
        assertThat(persistedCourse.getSections().get(0).getClasses().get(0).getDuration(), is(duration));

        verify(domainEventRepository).store(any(CourseSectionClassAddedDomainEvent.class));
    }

    @Test
    public void given_not_existing_course_when_send_post_request_then_receive_not_found_response() {

        url = String.format(url, CourseIdMother.random().getValue(), SectionIdMother.random().getValue());

        ResponseEntity<String> response = post(Map.of(
            "sectionClassId", SectionClassIdMother.random().getValue(),
            "title", SectionClassTitleMother.random().getValue(),
            "duration", DurationInSecondsMother.random().getValue()
        ));

        assertNotFound(response);
    }

    @Test
    public void given_existing_course_with_sections_when_send_post_request_for_a_not_existing_section_then_receive_not_found_response() {
        Course course = CourseMother.randomInDraftState();
        course.addSection(SectionIdMother.random(), SectionTitleMother.random());

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), SectionIdMother.random().getValue());

        ResponseEntity<String> response = post(Map.of(
            "sectionClassId", SectionClassIdMother.random().getValue(),
            "title", SectionClassTitleMother.random().getValue(),
            "duration", DurationInSecondsMother.random().getValue()
        ));

        assertNotFound(response);
    }

    @Test
    public void should_rollback_all_database_changes_when_an_exception_happen() {
        doThrow(RuntimeException.class).when(domainEventRepository).store(any());

        Course course = CourseMother.randomInDraftState();

        SectionId sectionId = SectionIdMother.random();
        course.addSection(sectionId, SectionTitleMother.random());

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), sectionId.getValue());

        post(Map.of(
            "sectionClassId", SectionClassIdMother.random().getValue(),
            "title", SectionClassTitleMother.random().getValue(),
            "duration", DurationInSecondsMother.random().getValue()
        ));

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getSections().get(0).getClasses().isEmpty(), is(true));
    }
}
