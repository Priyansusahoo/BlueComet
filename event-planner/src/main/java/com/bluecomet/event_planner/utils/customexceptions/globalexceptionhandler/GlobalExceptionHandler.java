package com.bluecomet.event_planner.utils.customexceptions.globalexceptionhandler;

import com.bluecomet.event_planner.utils.api.ApiErrorResponse;
import com.bluecomet.event_planner.utils.customexceptions.EventAlreadyCancelledException;
import com.bluecomet.event_planner.utils.customexceptions.EventNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
/**
 * Global exception handler for handling various exceptions occurring within the application.
 * <p>
 * This class provides centralized exception handling for API errors, ensuring consistent
 * error responses across the application.
 * </p>
 *
 * <p>
 * It captures specific exceptions such as {@link EventNotFoundException},
 * {@link IllegalArgumentException},
 * {@link EventAlreadyCancelledException}, {@link ConstraintViolationException},
 * {@link MethodArgumentNotValidException}, and generic exceptions, returning meaningful
 * error messages with appropriate HTTP status codes.
 * </p>
 *
 * <p>
 * Uses {@link RestControllerAdvice} to globally handle exceptions for REST controllers
 * and {@link Slf4j} for logging.
 * </p>
 *
 * @author Priyansu
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles {@link EventNotFoundException} when an event is not found.
     *
     * @param ex      The exception instance.
     * @param request The web request where the error occurred.
     * @return A structured {@link ApiErrorResponse} with a 404 NOT FOUND status.
     */
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEventNotFoundException(EventNotFoundException ex, WebRequest request) {
        log.warn("Event not found: {}", ex.getMessage());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles {@link EventAlreadyCancelledException} when an already cancelled event is attempted to be modified.
     *
     * @param ex      The exception instance.
     * @param request The web request where the error occurred.
     * @return A structured {@link ApiErrorResponse} with a 400 BAD REQUEST status.
     */
    @ExceptionHandler(EventAlreadyCancelledException.class)
    public ResponseEntity<ApiErrorResponse> handleEventAlreadyCancelledException(
            EventAlreadyCancelledException ex, WebRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles {@link IllegalArgumentException} when an illegal argument is provided.
     *
     * @param ex      The exception instance.
     * @param request The web request where the error occurred.
     * @return A structured {@link ApiErrorResponse} with a 400 BAD REQUEST status.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles validation errors from method arguments.
     *
     * @param ex      The exception instance containing validation errors.
     * @param request The web request where the error occurred.
     * @return A structured {@link ApiErrorResponse} with a 400 BAD REQUEST status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {

        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(errorMessage)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles constraint violations, typically from JPA or validation annotations.
     *
     * @param ex      The exception instance containing validation constraints.
     * @param request The web request where the error occurred.
     * @return A structured {@link ApiErrorResponse} with a 400 BAD REQUEST status.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {

        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .findFirst()
                .orElse("Constraint violation");

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(errorMessage)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles generic exceptions that are not explicitly caught by other handlers.
     *
     * @param ex      The exception instance.
     * @param request The web request where the error occurred.
     * @return A structured {@link ApiErrorResponse} with a 500 INTERNAL SERVER ERROR status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("An unexpected error occurred: " + ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


}
