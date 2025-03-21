package com.bluecomet.event_planner.model.exchange;

import com.bluecomet.event_planner.model.entity.EventRegistration;
import com.bluecomet.event_planner.model.vo.RegistrationStatus;

import java.time.LocalDateTime;
/**
 * Data Transfer Object (DTO) representing the response for an event registration{@link EventRegistration}.
 * This contains details about a user's registration for an event.
 *
 * @author Priyansu
 */

public record EventRegistrationResponse(
    Long id,
    Long eventId,
    Long userId,
    RegistrationStatus registrationStatus,
    LocalDateTime registeredAt
) {}
