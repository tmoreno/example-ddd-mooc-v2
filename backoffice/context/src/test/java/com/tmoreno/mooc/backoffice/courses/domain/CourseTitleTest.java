package com.tmoreno.mooc.backoffice.courses.domain;

import com.tmoreno.mooc.backoffice.courses.domain.exceptions.InvalidCourseTitleException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseTitleTest {
    @Test
    public void should_throws_an_exception_when_value_is_null() {
        assertThrows(InvalidCourseTitleException.class, () -> new CourseTitle(null));
    }

    @Test
    public void should_throws_an_exception_when_value_is_empty() {
        assertThrows(InvalidCourseTitleException.class, () -> new CourseTitle(""));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_long() {
        assertThrows(InvalidCourseTitleException.class, () -> new CourseTitle(RandomStringUtils.randomAlphabetic(501)));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_short() {
        assertThrows(InvalidCourseTitleException.class, () -> new CourseTitle(RandomStringUtils.randomAlphabetic(9)));
    }
}
