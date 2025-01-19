package com.tmoreno.mooc.frontoffice.common.controllers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ErrorResponse(
    @NotNull
    @Schema(description = "Http status code", example = "400")
    int status,

    @NotNull
    @Schema(description = "Error code", example = "INVALID_PARAMS")
    String code,

    @Schema(description = "Error description", example = "Any detailed error message")
    String message
) {
}
