package com.tmoreno.mooc.shared.domain.exceptions;

public final class NegativeMoneyValueException extends BaseDomainException {
    public NegativeMoneyValueException(double value) {
        super(
                "negative-money-value",
                "Money value is not positive: " + value
        );
    }
}
