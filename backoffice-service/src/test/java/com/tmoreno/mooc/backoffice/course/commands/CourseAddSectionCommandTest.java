package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.addSection.CourseAddSectionCommand;
import com.tmoreno.mooc.backoffice.course.commands.addSection.CourseAddSectionCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.SectionTitle;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionIdMother;
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
public class CourseAddSectionCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseAddSectionCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseAddSectionCommand(repository, eventBus);
    }

    @Test
    public void given_a_not_added_section_when_add_section_then_section_is_added_and_persisted_and_an_event_is_published() {
        Course course = CourseMother.randomInDraftState();
        SectionId sectionId = SectionIdMother.random();
        SectionTitle sectionTitle = SectionTitleMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseAddSectionCommandParams params = new CourseAddSectionCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setSectionId(sectionId.getValue());
        params.setTitle(sectionTitle.value());

        command.execute(params);

        assertThat(course.getSections().size(), is(1));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_add_section_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            CourseAddSectionCommandParams params = new CourseAddSectionCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setSectionId(SectionIdMother.random().getValue());
            params.setTitle(SectionTitleMother.random().value());

            command.execute(params);
        });
    }
}
