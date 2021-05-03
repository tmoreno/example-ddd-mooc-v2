package com.tmoreno.mooc.backoffice.courses.domain;

import org.apache.commons.lang3.RandomStringUtils;

public final class CourseImageUrlMother {
    public static CourseImageUrl random() {
        return new CourseImageUrl(RandomStringUtils.randomAlphabetic(10, 100));
    }
}
