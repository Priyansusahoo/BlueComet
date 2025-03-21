package com.bluecomet.event_planner.model.exchange;

import com.bluecomet.event_planner.model.vo.EventStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Data @NoArgsConstructor @AllArgsConstructor
public class EventRequest {

    @NotBlank(message = "Event name is required")
    @Size(max = 255, message = "Name must be under 255 characters")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Event date and time are required")
    @FutureOrPresent(message = "Event date must be in the future or present")
    private LocalDateTime eventDateTime;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotNull(message = "Status is required")
    private EventStatus status;
}