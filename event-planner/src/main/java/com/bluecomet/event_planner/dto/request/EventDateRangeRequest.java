package com.bluecomet.event_planner.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
/**
 * Represents a request object for fetching events within a specified date range.
 * <p>
 * Ensures that the start date is before the end date.
 * </p>
 *
 * @author Priyansu
 */
public record EventDateRangeRequest(

) {

}
