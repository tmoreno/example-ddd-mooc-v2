package com.tmoreno.mooc.shared.domain;

import org.apache.commons.lang3.RandomUtils;

public final class LanguageMother {
    public static Language random() {
        return Language.values()[RandomUtils.nextInt(0, Language.values().length)];
    }
}
