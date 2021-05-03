package com.tmoreno.mooc.shared.domain;

import com.tmoreno.mooc.shared.domain.exceptions.InvalidIdentifierException;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IdentifierTest {

    @Test
    public void should_throws_an_exception_when_value_is_null() {
        assertThrows(InvalidIdentifierException.class, () -> new Identifier(null));
    }

    @Test
    public void should_throws_an_exception_when_value_is_empty() {
        assertThrows(InvalidIdentifierException.class, () -> new Identifier(""));
    }

    @Test
    public void should_throws_an_exception_when_value_is_not_a_valid_identifier() {
        assertThrows(InvalidIdentifierException.class, () -> new Identifier("aaaa"));
    }

    @Test
    public void should_not_throws_an_exception_when_value_is_a_valid_identifier() {
        new Identifier(UUID.randomUUID().toString());
    }

    @Test
    public void check_two_equals_identifiers() {
        String value = UUID.randomUUID().toString();

        Identifier identifier1 = new Identifier(value);
        Identifier identifier2 = new Identifier(value);

        assertThat(identifier1, Is.is(identifier2));
    }

    @Test
    public void check_two_not_equals_identifiers() {
        Identifier identifier1 = new Identifier(UUID.randomUUID().toString());
        Identifier identifier2 = new Identifier(UUID.randomUUID().toString());

        assertThat(identifier1, not(Is.is(identifier2)));
    }
}
