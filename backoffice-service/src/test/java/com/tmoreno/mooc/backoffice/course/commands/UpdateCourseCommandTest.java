package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.updateCourse.UpdateCourseCommand;
import com.tmoreno.mooc.backoffice.course.commands.updateCourse.UpdateCourseCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseDescription;
import com.tmoreno.mooc.backoffice.course.domain.CourseImageUrl;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseSummary;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.common.mothers.CourseDescriptionMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseImageUrlMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseSummaryMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseTitleMother;
import com.tmoreno.mooc.shared.domain.Language;
import com.tmoreno.mooc.shared.domain.Price;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
import com.tmoreno.mooc.shared.mothers.PriceMother;
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
public class UpdateCourseCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private UpdateCourseCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new UpdateCourseCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_when_change_attribute_then_course_attribute_is_changed_and_events_are_published() {
        Course course = CourseMother.randomInDraftState();
        CourseTitle title = CourseTitleMother.random();
        CourseImageUrl imageUrl = CourseImageUrlMother.random();
        CourseSummary summary = CourseSummaryMother.random();
        CourseDescription description = CourseDescriptionMother.random();
        Language language = course.getLanguage().get() == Language.ENGLISH ? Language.SPANISH : Language.ENGLISH;
        Price price = PriceMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        UpdateCourseCommandParams params = new UpdateCourseCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setTitle(title.getValue());
        params.setImageUrl(imageUrl.getValue());
        params.setSummary(summary.getValue());
        params.setDescription(description.value());
        params.setLanguage(language.name());
        params.setPriceValue(price.value());
        params.setPriceCurrency(price.currency().getCurrencyCode());

        command.execute(params);

        assertThat(course.getTitle(), is(title));
        assertThat(course.getImageUrl(), is(Optional.of(imageUrl)));
        assertThat(course.getSummary(), is(Optional.of(summary)));
        assertThat(course.getDescription(), is(Optional.of(description)));
        assertThat(course.getLanguage(), is(Optional.of(language)));
        assertThat(course.getPrice(), is(Optional.of(price)));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(6));
    }

    @Test
    public void given_a_course_when_changed_attribute_value_is_the_same_then_course_attribute_is_not_changed_and_events_is_not_published() {
        Course course = CourseMother.randomInDraftState();
        CourseTitle title = course.getTitle();
        Optional<CourseImageUrl> imageUrl = course.getImageUrl();
        Optional<CourseSummary> summary = course.getSummary();
        Optional<CourseDescription> description = course.getDescription();
        Optional<Language> language = course.getLanguage();
        Optional<Price> price = course.getPrice();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        UpdateCourseCommandParams params = new UpdateCourseCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setTitle(title.getValue());
        params.setImageUrl(imageUrl.get().getValue());
        params.setSummary(summary.get().getValue());
        params.setDescription(description.get().value());
        params.setLanguage(language.get().name());
        params.setPriceValue(price.get().value());
        params.setPriceCurrency(price.get().currency().getCurrencyCode());

        command.execute(params);

        assertThat(course.getTitle(), is(title));
        assertThat(course.getImageUrl(), is(imageUrl));
        assertThat(course.getSummary(), is(summary));
        assertThat(course.getDescription(), is(description));
        assertThat(course.getLanguage(), is(language));
        assertThat(course.getPrice(), is(price));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().isEmpty(), is(true));
    }

    @Test
    public void given_a_course_when_attribute_to_change_is_null_then_course_attribute_is_changed_to_null_and_events_are_published() {
        Course course = CourseMother.randomInDraftState();
        CourseTitle title = course.getTitle();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        UpdateCourseCommandParams params = new UpdateCourseCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setTitle(title.getValue());

        command.execute(params);

        assertThat(course.getTitle(), is(title));
        assertThat(course.getImageUrl().isEmpty(), is(true));
        assertThat(course.getSummary().isEmpty(), is(true));
        assertThat(course.getDescription().isEmpty(), is(true));
        assertThat(course.getLanguage().isEmpty(), is(true));
        assertThat(course.getPrice().isEmpty(), is(true));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(5));
    }

    @Test
    public void given_a_not_existing_course_when_change_course_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            UpdateCourseCommandParams params = new UpdateCourseCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());

            command.execute(params);
        });
    }
}
