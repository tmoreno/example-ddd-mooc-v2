package com.tmoreno.mooc.backoffice.courses.domain;

import org.apache.commons.lang3.RandomStringUtils;

public final class CourseTitleMother {
    public static CourseTitle random() {
        return new CourseTitle(RandomStringUtils.randomAlphabetic(10, 500));
    }
}
