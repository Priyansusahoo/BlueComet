package com.bluecomet.event_planner.service.event_registration;

import com.bluecomet.event_planner.dto.registration.EventRegistrationRequest;
import com.bluecomet.event_planner.dto.registration.EventRegistrationResponse;
import com.bluecomet.event_planner.enums.RegistrationStatus;

import java.util.List;

public interface EventRegistrationService {
    EventRegistrationResponse registerUserForEvent(EventRegistrationRequest request);

    List<EventRegistrationResponse> getRegistrationsByEvent(Long eventId);

    List<EventRegistrationResponse> getRegistrationsByUser(Long userId);

    EventRegistrationResponse cancelRegistration(Long userId, Long eventId);

    void updateRegistrationStatus(Long registrationId, RegistrationStatus newStatus);
}
