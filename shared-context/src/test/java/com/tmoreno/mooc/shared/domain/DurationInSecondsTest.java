package com.tmoreno.mooc.shared.domain;

import com.tmoreno.mooc.shared.domain.exceptions.InvalidDurationException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DurationInSecondsTest {

    @Test
    public void should_throws_an_exception_when_duration_is_less_than_zero() {
        assertThrows(InvalidDurationException.class, () -> new DurationInSeconds(-1));
    }

    @Test
    public void should_throws_an_exception_when_duration_is_zero() {
        assertThrows(InvalidDurationException.class, () -> new DurationInSeconds(0));
    }

    @Test
    public void check_two_equals_durations() {
        DurationInSeconds duration1 = new DurationInSeconds(1);
        DurationInSeconds duration2 = new DurationInSeconds(1);

        assertThat(duration1, is(duration2));
    }

    @Test
    public void check_two_not_equals_durations() {
        DurationInSeconds duration1 = new DurationInSeconds(1);
        DurationInSeconds duration2 = new DurationInSeconds(2);

        assertThat(duration1, not(is(duration2)));
    }
}
