package com.tmoreno.mooc.backoffice.course.domain;

import com.tmoreno.mooc.backoffice.course.domain.exceptions.InvalidSectionClassTitleException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SectionClassTitleTest {
    @Test
    public void should_throws_an_exception_when_value_is_null() {
        assertThrows(InvalidSectionClassTitleException.class, () -> new SectionClassTitle(null));
    }

    @Test
    public void should_throws_an_exception_when_value_is_empty() {
        assertThrows(InvalidSectionClassTitleException.class, () -> new SectionClassTitle(""));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_long() {
        assertThrows(InvalidSectionClassTitleException.class, () -> new SectionClassTitle(RandomStringUtils.randomAlphabetic(101)));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_short() {
        assertThrows(InvalidSectionClassTitleException.class, () -> new SectionClassTitle(RandomStringUtils.randomAlphabetic(9)));
    }
}
