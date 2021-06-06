package com.tmoreno.mooc.backoffice.course;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSectionClassDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.SectionClassIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionClassTitleMother;
import com.tmoreno.mooc.backoffice.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionMother;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import com.tmoreno.mooc.shared.mothers.DurationInSecondsMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class CourseSectionClassDeleteControllerIT extends BaseControllerIT {

    @SpyBean
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        url += "/courses/%s/sections/%s/classes/%s";
    }

    @Test
    public void given_a_course_with_section_classes_when_send_delete_request_then_receive_ok_response_and_section_class_is_deleted_and_event_is_stored() {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        Section section = SectionMother.randomWithClass(sectionClassId, SectionClassTitleMother.random(), DurationInSecondsMother.random());
        Course course = CourseMother.randomInDraftStateWithSection(section);

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), section.getId().getValue(), sectionClassId.getValue());

        ResponseEntity<String> response = delete();

        assertOk(response);

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getSections().get(0).getClasses().isEmpty(), is(true));

        verify(domainEventRepository).store(any(CourseSectionClassDeletedDomainEvent.class));
    }

    @Test
    public void given_not_existing_course_when_send_delete_request_then_receive_not_found_response() {

        url = String.format(url, CourseIdMother.random().getValue(), SectionIdMother.random().getValue(), SectionClassIdMother.random().getValue());

        ResponseEntity<String> response = delete();

        assertNotFound(response);
    }

    @Test
    public void given_course_when_send_delete_request_for_a_not_existing_section_class_then_receive_not_found_response() {
        Section section = SectionMother.randomWithClass(SectionClassIdMother.random(), SectionClassTitleMother.random(), DurationInSecondsMother.random());
        Course course = CourseMother.randomInDraftStateWithSection(section);

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), section.getId().getValue(), SectionClassIdMother.random().getValue());

        ResponseEntity<String> response = delete();

        assertNotFound(response);
    }

    @Test
    public void given_course_without_section_class_when_send_delete_request_then_receive_not_found_response() {
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), section.getId().getValue(), SectionClassIdMother.random().getValue());

        ResponseEntity<String> response = delete();

        assertNotFound(response);
    }

    @Test
    public void should_rollback_all_database_changes_when_an_exception_happen() {
        doThrow(RuntimeException.class).when(domainEventRepository).store(any());

        SectionClassId sectionClassId = SectionClassIdMother.random();
        Section section = SectionMother.randomWithClass(sectionClassId, SectionClassTitleMother.random(), DurationInSecondsMother.random());
        Course course = CourseMother.randomInDraftStateWithSection(section);

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), section.getId().getValue(), sectionClassId.getValue());

        delete();

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getSections().get(0).getClasses().isEmpty(), is(false));
    }
}
