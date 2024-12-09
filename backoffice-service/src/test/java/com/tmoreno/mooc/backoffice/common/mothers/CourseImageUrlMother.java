package com.tmoreno.mooc.backoffice.common.mothers;

import com.tmoreno.mooc.backoffice.course.domain.CourseImageUrl;
import org.apache.commons.lang3.RandomStringUtils;

public final class CourseImageUrlMother {
    public static CourseImageUrl random() {
        return new CourseImageUrl(RandomStringUtils.randomAlphabetic(10, 100));
    }
}
