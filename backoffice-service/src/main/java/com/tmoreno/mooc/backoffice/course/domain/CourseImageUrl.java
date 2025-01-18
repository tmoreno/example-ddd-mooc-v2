package com.tmoreno.mooc.backoffice.course.domain;

import com.tmoreno.mooc.backoffice.course.domain.exceptions.InvalidCourseImageUrlException;
import org.apache.commons.lang3.StringUtils;

public record CourseImageUrl(
    String value
) {

    public CourseImageUrl {
        ensureValidImageUrl(value);
    }

    private void ensureValidImageUrl(String value) {
        if (StringUtils.isBlank(value)) {
            throw new InvalidCourseImageUrlException("Course image url can't be blank");
        }
    }
}
