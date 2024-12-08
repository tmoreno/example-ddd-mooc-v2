package com.tmoreno.mooc.backoffice.course.domain;

import com.tmoreno.mooc.backoffice.course.domain.exceptions.InvalidSectionTitleException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SectionTitleTest {
    @Test
    public void should_throws_an_exception_when_value_is_null() {
        assertThrows(InvalidSectionTitleException.class, () -> new SectionTitle(null));
    }

    @Test
    public void should_throws_an_exception_when_value_is_empty() {
        assertThrows(InvalidSectionTitleException.class, () -> new SectionTitle(""));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_long() {
        assertThrows(InvalidSectionTitleException.class, () -> new SectionTitle(RandomStringUtils.randomAlphabetic(101)));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_short() {
        assertThrows(InvalidSectionTitleException.class, () -> new SectionTitle(RandomStringUtils.randomAlphabetic(9)));
    }
}
