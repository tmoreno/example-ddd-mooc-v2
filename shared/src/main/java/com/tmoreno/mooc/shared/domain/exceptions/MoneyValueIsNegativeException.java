package com.tmoreno.mooc.shared.domain.exceptions;

public final class MoneyValueIsNegativeException extends RuntimeException {
    public MoneyValueIsNegativeException(double value) {
        super("Money value is not positive: " + value);
    }
}
