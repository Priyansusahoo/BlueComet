package com.bluecomet.event_planner.dto.registration;

import com.bluecomet.event_planner.entity.EventRegistration;
import com.bluecomet.event_planner.enums.RegistrationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
/**
 * Data Transfer Object (DTO) representing the response for an event registration{@link EventRegistration}.
 * This contains details about a user's registration for an event.
 *
 * @author Priyansu
 */
@Getter
@Builder
public class EventRegistrationResponse {
    private Long id;
    private Long eventId;
    private Long userId;
    private RegistrationStatus registrationStatus;
    private LocalDateTime registeredAt;
}
