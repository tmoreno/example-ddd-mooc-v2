package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.addSectionClass.CourseAddSectionClassCommand;
import com.tmoreno.mooc.backoffice.course.commands.addSectionClass.CourseAddSectionClassCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
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
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseAddSectionClassCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseAddSectionClassCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseAddSectionClassCommand(repository, eventBus);
    }

    @Test
    public void given_a_not_added_section_class_when_add_section_class_then_section_class_is_added_and_persisted_and_an_event_is_published() {
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseAddSectionClassCommandParams params = new CourseAddSectionClassCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setSectionId(section.getId().getValue());
        params.setSectionClassId(SectionClassIdMother.random().getValue());
        params.setTitle(SectionClassTitleMother.random().value());
        params.setDuration(DurationInSecondsMother.random().value());

        command.execute(params);

        assertThat(course.getSectionClasses(section.getId()).size(), is(1));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_add_section_class_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            CourseAddSectionClassCommandParams params = new CourseAddSectionClassCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setSectionId(SectionIdMother.random().getValue());
            params.setSectionClassId(SectionClassIdMother.random().getValue());
            params.setTitle(SectionClassTitleMother.random().value());
            params.setDuration(DurationInSecondsMother.random().value());

            command.execute(params);
        });
    }
}
