package com.tmoreno.mooc.backoffice.course.domain;

import com.tmoreno.mooc.backoffice.course.domain.exceptions.InvalidCourseTitleException;
import com.tmoreno.mooc.shared.domain.StringValueObject;
import org.apache.commons.lang3.StringUtils;

public final class CourseTitle extends StringValueObject {

    private static final int MAX_LENGTH = 500;
    private static final int MIN_LENGTH = 10;

    public CourseTitle(String value) {
        super(value);

        ensureValidTitle();
    }

    private void ensureValidTitle() {
        if (StringUtils.isBlank(value)) {
            throw new InvalidCourseTitleException("Course title can't be blank");
        }
        else if (value.length() > MAX_LENGTH) {
            throw new InvalidCourseTitleException("Course title length is more than " + MAX_LENGTH);
        }
        else if (value.length() < MIN_LENGTH) {
            throw new InvalidCourseTitleException("Course title length is less than " + MIN_LENGTH);
        }
    }
}
