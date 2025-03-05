package com.bluecomet.event_planner.dto.registration;

import com.bluecomet.event_planner.enums.RegistrationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventRegistrationResponse {
    private Long id;
    private Long eventId;
    private Long userId;
    private RegistrationStatus registrationStatus;
    private LocalDateTime registeredAt;
}
