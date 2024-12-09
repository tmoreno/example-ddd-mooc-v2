package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.updateSection.UpdateCourseSectionCommand;
import com.tmoreno.mooc.backoffice.course.commands.updateSection.UpdateCourseSectionCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionTitle;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionTitleMother;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
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
public class UpdateCourseSectionCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private UpdateCourseSectionCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new UpdateCourseSectionCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_when_change_section_title_then_section_title_is_changed_and_persisted_and_an_event_is_published() {
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);
        SectionTitle title = SectionTitleMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        UpdateCourseSectionCommandParams params = new UpdateCourseSectionCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setSectionId(section.getId().getValue());
        params.setTitle(title.getValue());

        command.execute(params);

        assertThat(course.getSections().get(0).getTitle(), is(title));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_course_when_change_section_title_with_same_value_then_section_title_is_not_changed_and_an_event_is_not_published() {
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        UpdateCourseSectionCommandParams params = new UpdateCourseSectionCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setSectionId(section.getId().getValue());
        params.setTitle(section.getTitle().getValue());

        command.execute(params);

        assertThat(course.getSections().get(0).getTitle(), is(section.getTitle()));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().isEmpty(), is(true));
    }

    @Test
    public void given_a_not_existing_course_when_change_section_title_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            UpdateCourseSectionCommandParams params = new UpdateCourseSectionCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setSectionId(SectionIdMother.random().getValue());
            params.setTitle(SectionTitleMother.random().getValue());

            command.execute(params);
        });
    }
}
