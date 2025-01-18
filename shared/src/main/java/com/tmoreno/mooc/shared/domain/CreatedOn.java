package com.tmoreno.mooc.shared.domain;

import java.time.Instant;

public record CreatedOn(
    Instant value
) {
    public long getValue() {
        return value.toEpochMilli();
    }
}
