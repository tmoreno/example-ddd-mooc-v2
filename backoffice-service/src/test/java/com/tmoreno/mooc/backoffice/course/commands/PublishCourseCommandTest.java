package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.publish.PublishCourseCommand;
import com.tmoreno.mooc.backoffice.course.commands.publish.PublishCourseCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseState;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
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
public class PublishCourseCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private PublishCourseCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new PublishCourseCommand(repository, eventBus);
    }

    @Test
    public void given_a_draft_course_when_publish_then_course_is_published_and_persisted_and_an_event_is_published() {
        Course course = CourseMother.randomReadyToPublish();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        PublishCourseCommandParams params = new PublishCourseCommandParams();
        params.setCourseId(course.getId().getValue());

        command.execute(params);

        assertThat(course.getState(), is(CourseState.PUBLISHED));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_publish_course_then_throws_an_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            PublishCourseCommandParams params = new PublishCourseCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());

            command.execute(params);
        });
    }
}
