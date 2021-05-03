package com.tmoreno.mooc.shared.domain;

import com.github.javafaker.Faker;

public final class EmailMother {

    public static Email random() {
        return new Email(Faker.instance().internet().emailAddress());
    }
}
