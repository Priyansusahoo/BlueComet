package com.bluecomet.event_planner.dto.event;

import com.bluecomet.event_planner.enums.EventStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * DTO for creating or updating an event.
 * <p>
 * This class is used to capture event details from client requests,
 * ensuring validation rules are applied before processing.
 * </p>
 *
 * @author Priyansu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {
    @NotBlank(message = "Event name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Event date and time is required")
    @FutureOrPresent(message = "Event date must be in the future or present")
    private LocalDateTime eventDateTime;

    private String description;

    @NotNull(message = "Status is required")
    private EventStatus status;
}