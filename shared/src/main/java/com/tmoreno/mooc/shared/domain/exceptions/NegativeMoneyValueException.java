package com.tmoreno.mooc.shared.domain.exceptions;

public final class NegativeMoneyValueException extends RuntimeException {
    public NegativeMoneyValueException(double value) {
        super("Money value is not positive: " + value);
    }
}
