package com.tmoreno.mooc.backoffice.course.domain;

import com.tmoreno.mooc.backoffice.course.domain.exceptions.InvalidReviewTextException;
import com.tmoreno.mooc.backoffice.review.domain.ReviewText;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReviewTextTest {
    @Test
    public void should_throws_an_exception_when_value_is_null() {
        assertThrows(InvalidReviewTextException.class, () -> new ReviewText(null));
    }

    @Test
    public void should_throws_an_exception_when_value_is_empty() {
        assertThrows(InvalidReviewTextException.class, () -> new ReviewText(""));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_long() {
        assertThrows(InvalidReviewTextException.class, () -> new ReviewText(RandomStringUtils.randomAlphabetic(5001)));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_short() {
        assertThrows(InvalidReviewTextException.class, () -> new ReviewText(RandomStringUtils.randomAlphabetic(99)));
    }
}
