package com.tmoreno.mooc.backoffice.common.infrastructure;

import java.time.Instant;

public final class ExceptionResponseBody {

    private final String code;
    private final int status;
    private final String message;
    private final String timestamp;

    public ExceptionResponseBody(String code, int status, String message, Instant timestamp) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp.toString();
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
