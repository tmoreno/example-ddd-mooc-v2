package com.tmoreno.mooc.backoffice.courses.domain;

import com.tmoreno.mooc.backoffice.courses.domain.exceptions.InvalidCourseImageUrlException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseImageUrlTest {

    @Test
    public void should_throws_an_exception_when_value_is_null() {
        assertThrows(InvalidCourseImageUrlException.class, () -> new CourseImageUrl(null));
    }

    @Test
    public void should_throws_an_exception_when_value_is_empty() {
        assertThrows(InvalidCourseImageUrlException.class, () -> new CourseImageUrl(""));
    }
}
