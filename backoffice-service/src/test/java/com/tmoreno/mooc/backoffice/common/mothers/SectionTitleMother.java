package com.tmoreno.mooc.backoffice.common.mothers;

import com.tmoreno.mooc.backoffice.course.domain.SectionTitle;
import org.apache.commons.lang3.RandomStringUtils;

public final class SectionTitleMother {
    public static SectionTitle random() {
        return new SectionTitle(RandomStringUtils.randomAlphabetic(10, 100));
    }
}
