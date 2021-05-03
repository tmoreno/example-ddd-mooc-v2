package com.tmoreno.mooc.shared.domain;

import org.apache.commons.lang3.RandomUtils;

public final class DurationInSecondsMother {
    public static DurationInSeconds random() {
        return new DurationInSeconds(RandomUtils.nextInt(1, 120));
    }
}
