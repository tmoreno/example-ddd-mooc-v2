package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.courses.domain.SectionClassTitle;
import org.apache.commons.lang3.RandomStringUtils;

public final class SectionClassTitleMother {
    public static SectionClassTitle random() {
        return new SectionClassTitle(RandomStringUtils.randomAlphabetic(10, 100));
    }
}
