package com.bluecomet.event_planner.service.api;

import com.bluecomet.event_planner.model.exchange.EventRegistrationRequest;
import com.bluecomet.event_planner.model.exchange.EventRegistrationResponse;
import com.bluecomet.event_planner.model.vo.RegistrationStatus;

import java.util.List;

public interface EventRegistrationService {
    EventRegistrationResponse registerUserForEvent(EventRegistrationRequest request);

    List<EventRegistrationResponse> getRegistrationsByEvent(Long eventId);

    List<EventRegistrationResponse> getRegistrationsByUser(Long userId);

    EventRegistrationResponse cancelRegistration(Long userId, Long eventId);

    void updateRegistrationStatus(Long registrationId, RegistrationStatus newStatus);
}
