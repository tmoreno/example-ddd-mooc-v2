package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.courses.domain.CourseTitle;
import org.apache.commons.lang3.RandomStringUtils;

public final class CourseTitleMother {
    public static CourseTitle random() {
        return new CourseTitle(RandomStringUtils.randomAlphabetic(10, 500));
    }
}
