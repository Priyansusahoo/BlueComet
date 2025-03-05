package com.bluecomet.event_planner.service.event_registration;

import com.bluecomet.event_planner.dto.registration.EventRegistrationRequest;
import com.bluecomet.event_planner.dto.registration.EventRegistrationResponse;
import com.bluecomet.event_planner.entity.Event;
import com.bluecomet.event_planner.entity.EventRegistration;
import com.bluecomet.event_planner.enums.RegistrationStatus;
import com.bluecomet.event_planner.repository.EventRegistrationRepository;
import com.bluecomet.event_planner.repository.EventRepository;
import com.bluecomet.event_planner.utils.customexceptions.EventNotFoundException;
import com.bluecomet.event_planner.utils.customexceptions.event_registration.RegistrationAlreadyCancelledException;
import com.bluecomet.event_planner.utils.customexceptions.event_registration.RegistrationAlreadyExistsException;
import com.bluecomet.event_planner.utils.customexceptions.event_registration.RegistrationNotFoundException;
import com.bluecomet.event_planner.utils.mapper.EventRegistrationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EventRegistrationServiceImpl implements EventRegistrationService {

    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRegistrationMapper eventRegistrationMapper;
    private final EventRepository eventRepository;

    @Override
    public EventRegistrationResponse registerUserForEvent(EventRegistrationRequest request) {
        // Check if event exists
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + request.getEventId() + " not found."));

        // Check if the user is already registered
        if (eventRegistrationRepository.existsByUserIdAndEventId(request.getUserId(), request.getEventId())) {
            throw new RegistrationAlreadyExistsException("User with ID " + request.getUserId() + " is already registered for this event.");
        }

        // Create new registration
        EventRegistration registration = EventRegistration.builder()
                .event(event)
                .userId(request.getUserId())
                .registrationStatus(RegistrationStatus.PENDING)
                .build();

        EventRegistration savedRegistration = eventRegistrationRepository.save(registration);

        return eventRegistrationMapper.toResponse(savedRegistration);
    }

    @Override
    public List<EventRegistrationResponse> getRegistrationsByEvent(Long eventId) {
        // Validate event existence before fetching registrations
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException("Event with ID " + eventId + " not found.");
        }
        List<EventRegistration> registrations = eventRegistrationRepository.findByEventId(eventId);
        return registrations.stream()
                .map(eventRegistrationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventRegistrationResponse> getRegistrationsByUser(Long userId) {
        List<EventRegistration> registrations = eventRegistrationRepository.findByUserId(userId);

        return registrations.isEmpty() ? Collections.emptyList() : registrations.stream()
                .map(eventRegistrationMapper::toResponse)
                .toList();
    }

    @Override
    public EventRegistrationResponse cancelRegistration(Long userId, Long eventId) {
        EventRegistration registration = eventRegistrationRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new RegistrationNotFoundException(
                        "Registration not found for user ID " + userId + " and event ID " + eventId));

        if (registration.getRegistrationStatus() == RegistrationStatus.CANCELLED) {
            throw new RegistrationAlreadyCancelledException("Registration is already cancelled.");
        }

        registration.setRegistrationStatus(RegistrationStatus.CANCELLED);
        EventRegistration updatedRegistration = eventRegistrationRepository.save(registration);

        return eventRegistrationMapper.toResponse(updatedRegistration);
    }

    @Override
    public void updateRegistrationStatus(Long registrationId, RegistrationStatus newStatus) {
        EventRegistration registration = eventRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new RegistrationNotFoundException("Registration with ID " + registrationId + " not found."));

        if (registration.getRegistrationStatus() == newStatus) {
            log.info("Registration ID {} already has status {}", registrationId, newStatus);
            return;
        }

        registration.setRegistrationStatus(newStatus);
        eventRegistrationRepository.save(registration);
    }
}
