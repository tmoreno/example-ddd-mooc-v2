package com.tmoreno.mooc.shared.mothers;

import com.tmoreno.mooc.shared.domain.CreatedOn;
import org.apache.commons.lang3.RandomUtils;

public final class CreatedOnMother {
    public static CreatedOn random() {
        return new CreatedOn(RandomUtils.nextLong());
    }
}
