package com.tmoreno.mooc.backoffice.courses.domain;

import com.tmoreno.mooc.backoffice.courses.domain.exceptions.InvalidCourseSummaryException;
import com.tmoreno.mooc.shared.domain.StringValueObject;
import org.apache.commons.lang3.StringUtils;

public final class CourseSummary extends StringValueObject {

    private static final int MAX_LENGTH = 1000;
    private static final int MIN_LENGTH = 100;

    public CourseSummary(String value) {
        super(value);

        ensureValidSummary();
    }

    private void ensureValidSummary() {
        if (StringUtils.isBlank(value)) {
            throw new InvalidCourseSummaryException("Course summary can't be blank");
        }
        else if (value.length() > MAX_LENGTH) {
            throw new InvalidCourseSummaryException("Course summary length is more than " + MAX_LENGTH);
        }
        else if (value.length() < MIN_LENGTH) {
            throw new InvalidCourseSummaryException("Course summary length is less than " + MIN_LENGTH);
        }
    }
}
