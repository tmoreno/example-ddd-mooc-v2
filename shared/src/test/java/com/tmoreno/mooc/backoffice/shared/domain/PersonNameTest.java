package com.tmoreno.mooc.backoffice.shared.domain;

import com.tmoreno.mooc.backoffice.shared.domain.exceptions.InvalidPersonNameException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonNameTest {

    @Test
    public void should_throws_an_exception_when_value_is_null() {
        assertThrows(InvalidPersonNameException.class, () -> new PersonName(null));
    }

    @Test
    public void should_throws_an_exception_when_value_is_empty() {
        assertThrows(InvalidPersonNameException.class, () -> new PersonName(""));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_long() {
        assertThrows(InvalidPersonNameException.class, () -> new PersonName(RandomStringUtils.randomAlphabetic(101)));
    }

    @Test
    public void should_throws_an_exception_when_value_is_too_short() {
        assertThrows(InvalidPersonNameException.class, () -> new PersonName(RandomStringUtils.randomAlphabetic(9)));
    }

    @Test
    public void check_two_equals_person_names() {
        String personName = RandomStringUtils.randomAlphabetic(10, 100);

        PersonName personName1 = new PersonName(personName);
        PersonName personName2 = new PersonName(personName);

        assertThat(personName1, is(personName2));
    }

    @Test
    public void check_two_not_equals_person_names() {
        PersonName personName1 = new PersonName(RandomStringUtils.randomAlphabetic(10, 100));
        PersonName personName2 = new PersonName(RandomStringUtils.randomAlphabetic(10, 100));

        assertThat(personName1, not(is(personName2)));
    }
}
