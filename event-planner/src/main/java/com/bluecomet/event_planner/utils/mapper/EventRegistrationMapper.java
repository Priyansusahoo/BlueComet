package com.bluecomet.event_planner.utils.mapper;

import com.bluecomet.event_planner.dto.registration.EventRegistrationResponse;
import com.bluecomet.event_planner.entity.EventRegistration;
import com.bluecomet.event_planner.utils.customexceptions.event_registration.RegistrationNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EventRegistrationMapper {

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
