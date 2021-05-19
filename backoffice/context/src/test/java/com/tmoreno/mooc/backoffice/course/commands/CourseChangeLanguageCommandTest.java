package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.changeLanguage.CourseChangeLanguageCommand;
import com.tmoreno.mooc.backoffice.course.commands.changeLanguage.CourseChangeLanguageCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.shared.domain.Language;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
import com.tmoreno.mooc.shared.mothers.LanguageMother;
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
public class CourseChangeLanguageCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseChangeLanguageCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseChangeLanguageCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_when_change_language_then_language_is_changed_and_persisted_and_an_event_is_published() {
        Course course = CourseMother.randomInDraftState();
        Language language = LanguageMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseChangeLanguageCommandParams params = new CourseChangeLanguageCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setLanguage(language.name());

        command.execute(params);

        assertThat(course.getLanguage(), is(Optional.of(language)));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_change_language_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            CourseChangeLanguageCommandParams params = new CourseChangeLanguageCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setLanguage(LanguageMother.random().name());

            command.execute(params);
        });
    }
}
