package com.tmoreno.mooc.backoffice.course.domain;

import com.tmoreno.mooc.backoffice.course.domain.exceptions.InvalidCourseDescriptionException;
import org.apache.commons.lang3.StringUtils;

public record CourseDescription(
    String value
) {

    private static final int MAX_LENGTH = 5000;
    private static final int MIN_LENGTH = 100;

    public CourseDescription {
        ensureValidDescription(value);
    }

    private void ensureValidDescription(String value) {
        if (StringUtils.isBlank(value)) {
            throw new InvalidCourseDescriptionException("Course description can't be blank");
        }
        else if (value.length() > MAX_LENGTH) {
            throw new InvalidCourseDescriptionException("Course description length is more than " + MAX_LENGTH);
        }
        else if (value.length() < MIN_LENGTH) {
            throw new InvalidCourseDescriptionException("Course description length is less than " + MIN_LENGTH);
        }
    }
}
