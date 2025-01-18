package com.tmoreno.mooc.backoffice.course.domain;

import com.tmoreno.mooc.backoffice.course.domain.exceptions.InvalidCourseSummaryException;
import org.apache.commons.lang3.StringUtils;

public record CourseSummary(
    String value
) {

    private static final int MAX_LENGTH = 1000;
    private static final int MIN_LENGTH = 100;

    public CourseSummary {
        ensureValidSummary(value);
    }

    private void ensureValidSummary(String value) {
        if (StringUtils.isBlank(value)) {
            throw new InvalidCourseSummaryException("Course summary can't be blank");
        } else if (value.length() > MAX_LENGTH) {
            throw new InvalidCourseSummaryException("Course summary length is more than " + MAX_LENGTH);
        } else if (value.length() < MIN_LENGTH) {
            throw new InvalidCourseSummaryException("Course summary length is less than " + MIN_LENGTH);
        }
    }
}
