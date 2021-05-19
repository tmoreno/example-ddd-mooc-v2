package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.changeImageUrl.CourseChangeImageUrlCommand;
import com.tmoreno.mooc.backoffice.course.commands.changeImageUrl.CourseChangeImageUrlCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseImageUrl;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseImageUrlMother;
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
public class CourseChangeImageUrlCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseChangeImageUrlCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseChangeImageUrlCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_when_change_image_url_then_image_url_is_changed_and_persisted_and_an_event_is_published() {
        Course course = CourseMother.randomInDraftState();
        CourseImageUrl imageUrl = CourseImageUrlMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseChangeImageUrlCommandParams params = new CourseChangeImageUrlCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setImageUrl(imageUrl.getValue());

        command.execute(params);

        assertThat(course.getImageUrl(), is(Optional.of(imageUrl)));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_change_image_url_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            CourseChangeImageUrlCommandParams params = new CourseChangeImageUrlCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setImageUrl(CourseImageUrlMother.random().getValue());

            command.execute(params);
        });
    }
}
