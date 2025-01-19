package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.create.CreateCourseCommand;
import com.tmoreno.mooc.backoffice.course.commands.create.CreateCourseCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseExistsException;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseTitleMother;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCourseCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CreateCourseCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CreateCourseCommand(repository, eventBus);
    }

    @Test
    public void given_a_not_existing_course_when_create_course_then_course_is_created_and_persisted_and_an_event_is_published() {
        CreateCourseCommandParams params = new CreateCourseCommandParams();
        params.setId(CourseIdMother.random().getValue());
        params.setTitle(CourseTitleMother.random().value());

        command.execute(params);

        verify(repository).save(any());

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_existing_course_when_create_course_then_throws_an_exception() {
        assertThrows(CourseExistsException.class, () -> {
            CourseId courseId = CourseIdMother.random();
            CourseTitle title = CourseTitleMother.random();

            when(repository.exists(courseId, title)).thenReturn(true);

            CreateCourseCommandParams params = new CreateCourseCommandParams();
            params.setId(courseId.getValue());
            params.setTitle(title.value());

            command.execute(params);
        });
    }
}
