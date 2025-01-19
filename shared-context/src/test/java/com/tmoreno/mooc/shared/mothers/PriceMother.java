package com.tmoreno.mooc.shared.mothers;

import com.tmoreno.mooc.shared.domain.Price;
import org.apache.commons.lang3.RandomUtils;

import java.util.Currency;
import java.util.Locale;

public final class PriceMother {
    public static Price random() {
        double priceValue = RandomUtils.nextDouble(1, 50);

        priceValue = Math.round(priceValue * 100.0) / 100.0;

        return new Price(priceValue, Currency.getInstance(Locale.US));
    }
}
