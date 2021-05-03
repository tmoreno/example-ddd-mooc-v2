package com.tmoreno.mooc.backoffice.shared.domain;

import com.tmoreno.mooc.backoffice.shared.domain.exceptions.MoneyValueIsNegativeException;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceTest {

    @Test
    public void should_throws_an_exception_when_price_is_less_than_zero() {
        assertThrows(MoneyValueIsNegativeException.class, () -> new Price(-1, Currency.getInstance(Locale.US)));
    }

    @Test
    public void should_not_throws_an_exception_when_price_is_zero() {
        new Price(0, Currency.getInstance(Locale.US));
    }

    @Test
    public void should_throws_an_exception_when_currency_is_null() {
        assertThrows(NullPointerException.class, () -> new Price(1, null));
    }

    @Test
    public void check_two_equals_prices() {
        Price price1 = new Price(1, Currency.getInstance(Locale.US));
        Price price2 = new Price(1, Currency.getInstance(Locale.US));

        assertThat(price1, is(price2));
    }

    @Test
    public void check_two_not_equals_prices() {
        Price price1 = new Price(1, Currency.getInstance(Locale.US));
        Price price2 = new Price(2, Currency.getInstance(Locale.US));

        assertThat(price1, not(is(price2)));
    }
}
