package com.tmoreno.mooc.backoffice.courses.domain;

import com.tmoreno.mooc.backoffice.courses.domain.exceptions.InvalidCourseImageUrlException;
import com.tmoreno.mooc.shared.domain.StringValueObject;
import org.apache.commons.lang3.StringUtils;

public final class CourseImageUrl extends StringValueObject {

    public CourseImageUrl(String value) {
        super(value);

        ensureValidImageUrl();
    }

    private void ensureValidImageUrl() {
        if (StringUtils.isBlank(value)) {
            throw new InvalidCourseImageUrlException("Course image url can't be blank");
        }
    }
}
