package com.bluecomet.event_planner.dto.registration;

import com.bluecomet.event_planner.entity.EventRegistration;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
/**
 * Data Transfer Object (DTO) representing the request for event registration {@link EventRegistration}.
 * This is used when a user registers for an event.
 *
 * @author Priyansu
 */
@Getter
@Setter
public class EventRegistrationRequest {
    @NotNull(message = "Event ID is required")
    private Long eventId;

    @NotNull(message = "User ID is required")
    private Long userId;
}
