package com.tmoreno.mooc.backoffice.shared.domain;

import org.apache.commons.lang3.RandomUtils;

public final class CreatedOnMother {
    public static CreatedOn random() {
        return new CreatedOn(RandomUtils.nextLong());
    }
}
