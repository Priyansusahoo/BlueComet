package com.bluecomet.event_planner.utils.mapper;

import com.bluecomet.event_planner.dto.registration.EventRegistrationResponse;
import com.bluecomet.event_planner.entity.EventRegistration;
import com.bluecomet.event_planner.utils.customexceptions.event_registration.RegistrationNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting event registration {@link EventRegistration} entities to response {@link EventRegistrationResponse} DTOs.
 *
 * @author Priyansu
 */
@Component
public class EventRegistrationMapper {

    /**
     * Converts an EventRegistration {@link EventRegistration} entity to a response DTO {@link EventRegistrationResponse}.
     *
     * @param registration The event registration entity.
     * @return The corresponding event registration response DTO.
     * @throws RegistrationNotFoundException If the registration is null.
     */
    public EventRegistrationResponse toResponse(EventRegistration registration) {

        if (registration == null)
        {
            throw new RegistrationNotFoundException("EventRegistration cannot be null");
        }

        return EventRegistrationResponse.builder()
                .id(registration.getId())
                .eventId(registration.getEvent().getId())
                .userId(registration.getUserId())
                .registrationStatus(registration.getRegistrationStatus())
                .registeredAt(registration.getRegisteredAt())
                .build();
    }
}
