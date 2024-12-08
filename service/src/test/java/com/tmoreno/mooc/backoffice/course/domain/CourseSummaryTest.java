package com.tmoreno.mooc.backoffice.course.domain;

import com.tmoreno.mooc.backoffice.course.domain.exceptions.InvalidCourseSummaryException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseSummaryTest {

    @Test
    public void should_throws_an_exception_when_value_is_null() {
        assertThrows(InvalidCourseSummaryException.class, () -> new CourseSummary(null));
    }

    @Test
    public void should_throws_an_exception_when_value_is_empty() {
        assertThrows(InvalidCourseSummaryException.class, () -> new CourseSummary(""));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_long() {
        assertThrows(InvalidCourseSummaryException.class, () -> new CourseSummary(RandomStringUtils.randomAlphabetic(1001)));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_short() {
        assertThrows(InvalidCourseSummaryException.class, () -> new CourseSummary(RandomStringUtils.randomAlphabetic(99)));
    }
}
