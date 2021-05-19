package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.changeSummary.CourseChangeSummaryCommand;
import com.tmoreno.mooc.backoffice.course.commands.changeSummary.CourseChangeSummaryCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseSummary;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.CourseSummaryMother;
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
public class CourseChangeSummaryCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseChangeSummaryCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseChangeSummaryCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_when_change_summary_then_summary_is_changed_and_persisted_and_an_event_is_published() {
        Course course = CourseMother.randomInDraftState();
        CourseSummary summary = CourseSummaryMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseChangeSummaryCommandParams params = new CourseChangeSummaryCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setSummary(summary.getValue());

        command.execute(params);

        assertThat(course.getSummary(), is(Optional.of(summary)));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_change_summary_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            CourseChangeSummaryCommandParams params = new CourseChangeSummaryCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setSummary(CourseSummaryMother.random().getValue());

            command.execute(params);
        });
    }
}
