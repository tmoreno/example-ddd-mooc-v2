package com.tmoreno.mooc.backoffice.courses.domain;

import org.apache.commons.lang3.RandomStringUtils;

public final class CourseSummaryMother {
    public static CourseSummary random() {
        return new CourseSummary(RandomStringUtils.randomAlphabetic(100, 1000));
    }
}
