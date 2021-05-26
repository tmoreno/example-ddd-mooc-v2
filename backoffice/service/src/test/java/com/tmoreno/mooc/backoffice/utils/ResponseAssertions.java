package com.tmoreno.mooc.backoffice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public final class ResponseAssertions {

    public static void assertCreated(ResponseEntity<String> response) {
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.hasBody(), is(false));
    }

}
