package com.tmoreno.mooc.shared.mothers;

import com.tmoreno.mooc.shared.domain.PersonName;
import org.apache.commons.lang3.RandomStringUtils;

public final class PersonNameMother {
    public static PersonName random() {
        return new PersonName(RandomStringUtils.randomAlphabetic(10, 100));
    }
}
