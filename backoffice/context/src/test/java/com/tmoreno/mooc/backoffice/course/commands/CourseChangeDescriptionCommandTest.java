package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.changeDescription.CourseChangeDescriptionCommand;
import com.tmoreno.mooc.backoffice.course.commands.changeDescription.CourseChangeDescriptionCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseDescription;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.mothers.CourseDescriptionMother;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
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
public class CourseChangeDescriptionCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseChangeDescriptionCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseChangeDescriptionCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_when_change_description_then_description_is_changed_and_persisted_and_an_event_is_published() {
        Course course = CourseMother.randomInDraftState();
        CourseDescription description = CourseDescriptionMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseChangeDescriptionCommandParams params = new CourseChangeDescriptionCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setDescription(description.getValue());

        command.execute(params);

        assertThat(course.getDescription(), is(Optional.of(description)));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_change_description_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            CourseChangeDescriptionCommandParams params = new CourseChangeDescriptionCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setDescription(CourseDescriptionMother.random().getValue());

            command.execute(params);
        });
    }
}
