package com.tmoreno.mooc.shared.domain;

import com.tmoreno.mooc.shared.domain.exceptions.InvalidPersonNameException;
import org.apache.commons.lang3.StringUtils;

public record PersonName(
    String value
) {

    private static final int MAX_LENGTH = 100;
    private static final int MIN_LENGTH = 10;

    public PersonName {
        ensureValidName(value);
    }

    private void ensureValidName(String value) {
        if (StringUtils.isBlank(value)) {
            throw new InvalidPersonNameException("Person name can't be blank");
        } else if (value.length() > MAX_LENGTH) {
            throw new InvalidPersonNameException("Person name length is more than " + MAX_LENGTH);
        } else if (value.length() < MIN_LENGTH) {
            throw new InvalidPersonNameException("Person name length is less than " + MIN_LENGTH);
        }
    }
}
