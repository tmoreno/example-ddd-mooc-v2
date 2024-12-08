package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.deleteSection.CourseDeleteSectionCommand;
import com.tmoreno.mooc.backoffice.course.commands.deleteSection.CourseDeleteSectionCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionMother;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
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
public class CourseDeleteSectionCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseDeleteSectionCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseDeleteSectionCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_with_section_when_delete_section_then_section_is_deleted_and_persisted_and_an_event_is_published() {
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseDeleteSectionCommandParams params = new CourseDeleteSectionCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setSectionId(section.getId().getValue());

        command.execute(params);

        assertThat(course.getSections(), is(empty()));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_delete_section_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            CourseDeleteSectionCommandParams params = new CourseDeleteSectionCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setSectionId(SectionIdMother.random().getValue());

            command.execute(params);
        });
    }
}
