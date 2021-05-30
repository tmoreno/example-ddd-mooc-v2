package com.tmoreno.mooc.shared.mothers;

import com.github.javafaker.Faker;
import com.tmoreno.mooc.shared.domain.CreatedOn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class CreatedOnMother {
    public static CreatedOn random() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            Date from = format.parse("01/01/1975 00:00:00");
            Date to = format.parse("31/12/2055 23:59:59");

            return new CreatedOn(Faker.instance().date().between(from, to).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
