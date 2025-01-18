package com.tmoreno.mooc.backoffice.course.domain;

import com.tmoreno.mooc.backoffice.course.domain.exceptions.InvalidSectionTitleException;
import org.apache.commons.lang3.StringUtils;

public record SectionTitle(
    String value
) {

    private static final int MAX_LENGTH = 100;
    private static final int MIN_LENGTH = 10;

    public SectionTitle {
        ensureValidTitle(value);
    }

    private void ensureValidTitle(String value) {
        if (StringUtils.isBlank(value)) {
            throw new InvalidSectionTitleException("Section title can't be blank");
        } else if (value.length() > MAX_LENGTH) {
            throw new InvalidSectionTitleException("Section title length is more than " + MAX_LENGTH);
        } else if (value.length() < MIN_LENGTH) {
            throw new InvalidSectionTitleException("Section title length is less than " + MIN_LENGTH);
        }
    }
}
