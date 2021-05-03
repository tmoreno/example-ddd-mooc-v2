package com.tmoreno.mooc.shared.utils;

import com.github.javafaker.Faker;
import com.tmoreno.mooc.shared.domain.Email;

public final class EmailMother {

    public static Email random() {
        return new Email(Faker.instance().internet().emailAddress());
    }
}
