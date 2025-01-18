package com.tmoreno.mooc.shared.domain;

import com.tmoreno.mooc.shared.domain.exceptions.NegativeMoneyValueException;

import java.util.Currency;
import java.util.Objects;

public record Price(
    double value,
    Currency currency
) {
    public Price {
        ensureValidValue(value);
        Objects.requireNonNull(currency, "Currency value should be not null");
    }

    private void ensureValidValue(double value) {
        if (value < 0) {
            throw new NegativeMoneyValueException(value);
        }
    }
}
