package com.tmoreno.mooc.backoffice.courses.domain;

import org.apache.commons.lang3.RandomStringUtils;

public final class CourseDescriptionMother {
    public static CourseDescription random() {
        return new CourseDescription(RandomStringUtils.randomAlphabetic(100, 5000));
    }
}
