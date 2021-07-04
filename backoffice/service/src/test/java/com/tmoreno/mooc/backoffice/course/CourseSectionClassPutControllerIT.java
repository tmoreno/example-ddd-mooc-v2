package com.tmoreno.mooc.backoffice.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassTitle;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.SectionClassIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionClassTitleMother;
import com.tmoreno.mooc.backoffice.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionMother;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;
import com.tmoreno.mooc.shared.mothers.DurationInSecondsMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertErrorCode;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CourseSectionClassPutControllerIT extends BaseControllerIT {

    @SpyBean
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        url += "/courses/%s/sections/%s/classes";
    }

    @Test
    public void given_a_course_with_sections_and_classes_when_send_put_request_then_receive_ok_response_and_section_class_is_changed_and_event_is_stored() {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        Section section = SectionMother.randomWithClass(sectionClassId, SectionClassTitleMother.random(), DurationInSecondsMother.random());
        Course course = CourseMother.randomInDraftStateWithSection(section);
        courseRepository.save(course);

        SectionClassTitle title = SectionClassTitleMother.random();
        DurationInSeconds duration = DurationInSecondsMother.random();

        url = String.format(url, course.getId().getValue(), section.getId().getValue());

        ResponseEntity<String> response = put(
            sectionClassId.getValue(),
            Map.of(
                "title", title.getValue(),
                "duration", duration.getValue()
            )
        );

        assertOk(response);

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getSections().get(0).getClasses().get(0).getTitle(), is(title));
        assertThat(persistedCourse.getSections().get(0).getClasses().get(0).getDuration(), is(duration));

        verify(domainEventRepository, times(2)).store(any());
    }

    @Test
    public void given_a_course_with_classes_when_send_put_request_with_same_values_then_receive_ok_response_and_class_is_not_changed_and_event_is_not_stored() {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        SectionClassTitle title = SectionClassTitleMother.random();
        DurationInSeconds duration = DurationInSecondsMother.random();
        Section section = SectionMother.randomWithClass(sectionClassId, title, duration);
        Course course = CourseMother.randomInDraftStateWithSection(section);

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), section.getId().getValue());

        ResponseEntity<String> response = put(
            sectionClassId.getValue(),
            Map.of(
                "title", title.getValue(),
                "duration", duration.getValue()
            )
        );

        assertOk(response);

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getSections().get(0).getClasses().get(0).getTitle(), is(title));
        assertThat(persistedCourse.getSections().get(0).getClasses().get(0).getDuration(), is(duration));

        verify(domainEventRepository, never()).store(any());
    }

    @Test
    public void given_not_existing_course_when_send_put_request_then_receive_not_found_response() throws JsonProcessingException {
        url = String.format(url, CourseIdMother.random().getValue(), SectionIdMother.random().getValue());

        ResponseEntity<String> response = put(SectionClassIdMother.random().getValue(), Map.of());

        assertNotFound(response);

        assertErrorCode(toJson(response.getBody()), "course-not-found");
    }

    @Test
    public void given_a_course_with_classes_when_send_put_request_for_a_not_existing_class_then_receive_not_found_response() throws JsonProcessingException {
        Section section = SectionMother.randomWithClass(
                SectionClassIdMother.random(),
                SectionClassTitleMother.random(),
                DurationInSecondsMother.random()
        );
        Course course = CourseMother.randomInDraftStateWithSection(section);

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), section.getId().getValue());

        ResponseEntity<String> response = put(SectionIdMother.random().getValue(), Map.of());

        assertNotFound(response);

        assertErrorCode(toJson(response.getBody()), "course-section-class-not-found");
    }

    @Test
    public void should_rollback_all_database_changes_when_an_exception_happen() {
        doThrow(RuntimeException.class).when(domainEventRepository).store(any());

        SectionClassId sectionClassId = SectionClassIdMother.random();
        SectionClassTitle title = SectionClassTitleMother.random();
        DurationInSeconds duration = DurationInSecondsMother.random();
        Section section = SectionMother.randomWithClass(sectionClassId, title, duration);
        Course course = CourseMother.randomInDraftStateWithSection(section);
        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), section.getId().getValue());

        put(
            sectionClassId.getValue(),
            Map.of(
                "title", SectionClassTitleMother.random().getValue(),
                "duration", DurationInSecondsMother.random().getValue()
            )
        );

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getSections().get(0).getClasses().get(0).getTitle(), is(title));
        assertThat(persistedCourse.getSections().get(0).getClasses().get(0).getDuration(), is(duration));
    }
}
