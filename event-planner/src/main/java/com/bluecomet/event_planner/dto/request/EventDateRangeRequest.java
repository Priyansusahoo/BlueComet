package com.bluecomet.event_planner.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record EventDateRangeRequest(
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
) {
    public void validate() {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
    }
}
