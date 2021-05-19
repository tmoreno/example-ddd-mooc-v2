package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.changeSectionTitle.CourseChangeSectionTitleCommand;
import com.tmoreno.mooc.backoffice.course.commands.changeSectionTitle.CourseChangeSectionTitleCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionTitle;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionMother;
import com.tmoreno.mooc.backoffice.mothers.SectionTitleMother;
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
public class CourseChangeSectionTitleCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseChangeSectionTitleCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseChangeSectionTitleCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_when_change_section_title_then_section_title_is_changed_and_persisted_and_an_event_is_published() {
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);
        SectionTitle title = SectionTitleMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseChangeSectionTitleCommandParams params = new CourseChangeSectionTitleCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setSectionId(section.getId().getValue());
        params.setTitle(title.getValue());

        command.execute(params);

        assertThat(course.getSections().get(0).getTitle(), is(title));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_change_section_title_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            CourseChangeSectionTitleCommandParams params = new CourseChangeSectionTitleCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setSectionId(SectionIdMother.random().getValue());
            params.setTitle(SectionTitleMother.random().getValue());

            command.execute(params);
        });
    }
}
