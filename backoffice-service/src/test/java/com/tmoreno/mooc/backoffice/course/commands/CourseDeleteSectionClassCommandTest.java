package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.deleteSectionClass.CourseDeleteSectionClassCommand;
import com.tmoreno.mooc.backoffice.course.commands.deleteSectionClass.CourseDeleteSectionClassCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionClassIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionClassTitleMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionMother;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
import com.tmoreno.mooc.shared.mothers.DurationInSecondsMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseDeleteSectionClassCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseDeleteSectionClassCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseDeleteSectionClassCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_with_section_class_when_delete_section_class_then_section_class_is_deleted_and_persisted_and_an_event_is_published() {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        Section section = SectionMother.randomWithClass(
                sectionClassId,
                SectionClassTitleMother.random(),
                DurationInSecondsMother.random()
        );
        Course course = CourseMother.randomInDraftStateWithSection(section);

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseDeleteSectionClassCommandParams params = new CourseDeleteSectionClassCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setSectionId(section.getId().getValue());
        params.setSectionClassId(sectionClassId.getValue());

        command.execute(params);

        assertThat(course.getSections().get(0).getClasses(), is(empty()));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_delete_section_class_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            CourseDeleteSectionClassCommandParams params = new CourseDeleteSectionClassCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setSectionId(SectionIdMother.random().getValue());
            params.setSectionClassId(SectionClassIdMother.random().getValue());

            command.execute(params);
        });
    }
}
