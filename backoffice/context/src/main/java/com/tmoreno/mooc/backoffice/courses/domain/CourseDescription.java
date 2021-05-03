package com.tmoreno.mooc.backoffice.courses.domain;

import com.tmoreno.mooc.backoffice.courses.domain.exceptions.InvalidCourseDescriptionException;
import com.tmoreno.mooc.shared.domain.StringValueObject;
import org.apache.commons.lang3.StringUtils;

public final class CourseDescription extends StringValueObject {

    private static final int MAX_LENGTH = 5000;
    private static final int MIN_LENGTH = 100;

    public CourseDescription(String value) {
        super(value);

        ensureValidDescription();
    }

    private void ensureValidDescription() {
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
