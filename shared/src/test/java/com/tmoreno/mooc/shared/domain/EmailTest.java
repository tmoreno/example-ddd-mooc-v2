package com.tmoreno.mooc.shared.domain;

import com.tmoreno.mooc.shared.domain.exceptions.InvalidEmailException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailTest {

    @Test
    public void should_throws_an_exception_when_value_is_null() {
        assertThrows(InvalidEmailException.class, () -> new Email(null));
    }

    @Test
    public void should_throws_an_exception_when_value_is_empty() {
        assertThrows(InvalidEmailException.class, () -> new Email(""));
    }

    @Test
    public void should_throws_an_exception_when_value_is_not_a_valid_email() {
        assertThrows(InvalidEmailException.class, () -> new Email("aaaa"));
    }

    @Test
    public void should_not_throws_an_exception_when_value_is_a_valid_email() {
        new Email("test@example.com");
    }

    @Test
    public void check_two_equals_emails() {
        Email email1 = new Email("test@example.com");
        Email email2 = new Email("test@example.com");

        assertThat(email1, is(email2));
    }

    @Test
    public void check_two_not_equals_emails() {
        Email email1 = new Email("test1@example.com");
        Email email2 = new Email("test2@example.com");

        assertThat(email1, not(is(email2)));
    }
}
