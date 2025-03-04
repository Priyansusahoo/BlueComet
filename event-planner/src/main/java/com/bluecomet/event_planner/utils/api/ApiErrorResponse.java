package com.bluecomet.event_planner.utils.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * Represents a standardized error response for API exceptions.
 * <p>
 * This class is used to provide a structured JSON response when an API request fails.
 * It includes details such as the timestamp of the error, HTTP status code, error type,
 * a descriptive message, and the request path that caused the error.
 * </p>
 *
 * <p>
 * The class follows the builder pattern for easy instantiation and ensures immutability
 * with {@link Getter}.
 * </p>
 *
 * @author Priyansu
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
