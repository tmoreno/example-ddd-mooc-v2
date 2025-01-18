package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.updateSectionClass.UpdateCourseSectionClassCommand;
import com.tmoreno.mooc.backoffice.course.commands.updateSectionClass.UpdateCourseSectionClassCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassTitle;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionClassIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionClassTitleMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionMother;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;
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
public class UpdateCourseSectionClassCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private UpdateCourseSectionClassCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new UpdateCourseSectionClassCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_when_change_section_class_attributes_then_section_class_attributes_are_changed_and_events_are_published() {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        Section section = SectionMother.randomWithClass(
                sectionClassId,
                SectionClassTitleMother.random(),
                DurationInSecondsMother.random()
        );
        Course course = CourseMother.randomInDraftStateWithSection(section);
        SectionClassTitle title = SectionClassTitleMother.random();
        DurationInSeconds duration = DurationInSecondsMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        UpdateCourseSectionClassCommandParams params = new UpdateCourseSectionClassCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setSectionId(section.getId().getValue());
        params.setSectionClassId(sectionClassId.getValue());
        params.setTitle(title.getValue());
        params.setDuration(duration.value());

        command.execute(params);

        assertThat(course.getSectionClasses(section.getId()).get(0).getTitle(), is(title));
        assertThat(course.getSectionClasses(section.getId()).get(0).getDuration(), is(duration));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(2));
    }

    @Test
    public void given_a_course_when_changed_attribute_value_is_the_same_then_section_class_attributes_are_not_changed_and_events_are_not_published() {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        SectionClassTitle title = SectionClassTitleMother.random();
        DurationInSeconds duration = DurationInSecondsMother.random();
        Section section = SectionMother.randomWithClass(sectionClassId, title, duration);
        Course course = CourseMother.randomInDraftStateWithSection(section);

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        UpdateCourseSectionClassCommandParams params = new UpdateCourseSectionClassCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setSectionId(section.getId().getValue());
        params.setSectionClassId(sectionClassId.getValue());
        params.setTitle(title.getValue());
        params.setDuration(duration.value());

        command.execute(params);

        assertThat(course.getSectionClasses(section.getId()).get(0).getTitle(), is(title));
        assertThat(course.getSectionClasses(section.getId()).get(0).getDuration(), is(duration));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().isEmpty(), is(true));
    }

    @Test
    public void given_a_not_existing_course_when_change_section_class_title_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            UpdateCourseSectionClassCommandParams params = new UpdateCourseSectionClassCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setSectionId(SectionIdMother.random().getValue());
            params.setSectionClassId(SectionClassIdMother.random().getValue());
            params.setTitle(SectionClassTitleMother.random().getValue());
            params.setDuration(DurationInSecondsMother.random().value());

            command.execute(params);
        });
    }
}
