package com.tmoreno.mooc.backoffice.courses.domain;

import com.tmoreno.mooc.backoffice.courses.domain.exceptions.InvalidCourseDescriptionException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseDescriptionTest {

    @Test
    public void should_throws_an_exception_when_value_is_null() {
        assertThrows(InvalidCourseDescriptionException.class, () -> new CourseDescription(null));
    }

    @Test
    public void should_throws_an_exception_when_value_is_empty() {
        assertThrows(InvalidCourseDescriptionException.class, () -> new CourseDescription(""));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_long() {
        assertThrows(InvalidCourseDescriptionException.class, () -> new CourseDescription(RandomStringUtils.randomAlphabetic(5001)));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_short() {
        assertThrows(InvalidCourseDescriptionException.class, () -> new CourseDescription(RandomStringUtils.randomAlphabetic(99)));
    }
}
