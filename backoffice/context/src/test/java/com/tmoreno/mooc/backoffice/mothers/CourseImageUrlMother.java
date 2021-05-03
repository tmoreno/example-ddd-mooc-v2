package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.courses.domain.CourseImageUrl;
import org.apache.commons.lang3.RandomStringUtils;

public final class CourseImageUrlMother {
    public static CourseImageUrl random() {
        return new CourseImageUrl(RandomStringUtils.randomAlphabetic(10, 100));
    }
}
