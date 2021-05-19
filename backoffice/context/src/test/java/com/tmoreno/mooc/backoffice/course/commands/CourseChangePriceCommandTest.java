package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.changePrice.CourseChangePriceCommand;
import com.tmoreno.mooc.backoffice.course.commands.changePrice.CourseChangePriceCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
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
public class CourseChangePriceCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseChangePriceCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseChangePriceCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_when_change_price_then_price_is_changed_and_persisted_and_an_event_is_published() {
        Course course = CourseMother.randomInDraftState();
        Price price = PriceMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseChangePriceCommandParams params = new CourseChangePriceCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setValue(price.getValue());
        params.setCurrency(price.getCurrency().getCurrencyCode());

        command.execute(params);

        assertThat(course.getPrice(), is(Optional.of(price)));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_change_price_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            Price price = PriceMother.random();
            
            CourseChangePriceCommandParams params = new CourseChangePriceCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setValue(price.getValue());
            params.setCurrency(price.getCurrency().getCurrencyCode());

            command.execute(params);
        });
    }
}
