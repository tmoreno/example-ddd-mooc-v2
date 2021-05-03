package com.tmoreno.mooc.backoffice.courses.domain;

import org.apache.commons.lang3.RandomStringUtils;

public final class SectionClassTitleMother {
    public static SectionClassTitle random() {
        return new SectionClassTitle(RandomStringUtils.randomAlphabetic(10, 100));
    }
}
