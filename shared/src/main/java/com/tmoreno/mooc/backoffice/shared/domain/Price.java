package com.tmoreno.mooc.backoffice.shared.domain;

import com.tmoreno.mooc.backoffice.shared.domain.exceptions.MoneyValueIsNegativeException;

import java.util.Currency;
import java.util.Objects;

public final class Price {
    private final double value;
    private final Currency currency;

    public Price(double value, Currency currency) {
        ensureValidValue(value);
        Objects.requireNonNull(currency, "Currency value should be not null");

        this.value = value;
        this.currency = currency;
    }

    private void ensureValidValue(double value) {
        if (value < 0) {
            throw new MoneyValueIsNegativeException(value);
        }
    }

    public double getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Double.compare(price.value, value) == 0 && Objects.equals(currency, price.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }
}
