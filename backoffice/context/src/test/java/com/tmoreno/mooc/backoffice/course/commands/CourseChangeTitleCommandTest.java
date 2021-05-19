package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.changeTitle.CourseChangeTitleCommand;
import com.tmoreno.mooc.backoffice.course.commands.changeTitle.CourseChangeTitleCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.CourseTitleMother;
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
public class CourseChangeTitleCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseChangeTitleCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseChangeTitleCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_when_change_title_then_title_is_changed_and_persisted_and_an_event_is_published() {
        Course course = CourseMother.randomInDraftState();
        CourseTitle title = CourseTitleMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseChangeTitleCommandParams params = new CourseChangeTitleCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setTitle(title.getValue());

        command.execute(params);

        assertThat(course.getTitle(), is(title));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_change_title_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            CourseChangeTitleCommandParams params = new CourseChangeTitleCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setTitle(CourseTitleMother.random().getValue());

            command.execute(params);
        });
    }
}
