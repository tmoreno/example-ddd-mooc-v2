package com.tmoreno.mooc.backoffice.courses.domain;

import org.apache.commons.lang3.RandomStringUtils;

public final class SectionTitleMother {
    public static SectionTitle random() {
        return new SectionTitle(RandomStringUtils.randomAlphabetic(10, 100));
    }
}
