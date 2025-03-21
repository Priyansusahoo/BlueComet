package com.bluecomet.event_planner.service.impl;

import com.bluecomet.event_planner.model.exchange.EventRegistrationRequest;
import com.bluecomet.event_planner.model.exchange.EventRegistrationResponse;
import com.bluecomet.event_planner.model.entity.Event;
import com.bluecomet.event_planner.model.entity.EventRegistration;
import com.bluecomet.event_planner.model.vo.RegistrationStatus;
import com.bluecomet.event_planner.repository.EventRegistrationRepository;
import com.bluecomet.event_planner.repository.EventRepository;
import com.bluecomet.event_planner.service.api.EventRegistrationService;
import com.bluecomet.event_planner.exception.EventNotFoundException;
import com.bluecomet.event_planner.exception.RegistrationAlreadyCancelledException;
import com.bluecomet.event_planner.exception.RegistrationAlreadyExistsException;
import com.bluecomet.event_planner.exception.RegistrationNotFoundException;
import com.bluecomet.event_planner.mapper.EventRegistrationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service for handling event registration logic.
 *
 * @author Priyansu
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EventRegistrationServiceImpl implements EventRegistrationService {

    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRegistrationMapper eventRegistrationMapper;
    private final EventRepository eventRepository;

    /**
     * Registers a user for an event.
     *
     * @param request The registration request containing user ID and event ID {@link EventRegistrationRequest}.
     * @return The event registration response object {@link EventRegistrationResponse}.
     * @throws EventNotFoundException If the event ID does not exist.
     * @throws RegistrationAlreadyExistsException If the user is already registered.
     */
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

    /**
     * Retrieves all registrations for a given event.
     *
     * @param eventId The ID of the event.
     * @return A list of event registrations, or an empty list if none exist.
     * @throws EventNotFoundException if the event does not exist.
     */
    @Override
    public List<EventRegistrationResponse> getRegistrationsByEvent(Long eventId) {
        // Validate event existence before fetching registrations
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException("Event with ID " + eventId + " not found.");
        }
        List<EventRegistration> registrations = eventRegistrationRepository.findByEventId(eventId);
        return registrations.isEmpty() ? Collections.emptyList() : registrations.stream()
                .map(eventRegistrationMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of event registrations for a specific user.
     *
     * @param userId the ID of the user whose registrations are to be retrieved
     * @return a list of {@link EventRegistrationResponse} objects
     */
    @Override
    public List<EventRegistrationResponse> getRegistrationsByUser(Long userId) {
        List<EventRegistration> registrations = eventRegistrationRepository.findByUserId(userId);

        return registrations.isEmpty() ? Collections.emptyList() : registrations.stream()
                .map(eventRegistrationMapper::toResponse)
                .toList();
    }

    /**
     * Cancels an existing registration if it is not already cancelled.
     *
     * @param userId  The ID of the user whose registration is to be canceled.
     * @param eventId The ID of the event.
     * @return The updated registration details.
     * @throws RegistrationNotFoundException         if the registration does not exist.
     * @throws RegistrationAlreadyCancelledException if the registration is already canceled.
     */
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

    /**
     * Updates the status of an event registration.
     * If the provided status is already set, no update is performed.
     *
     * @param registrationId The unique ID of the event registration to update.
     * @param newStatus The new status to set for the registration.
     * @throws RegistrationNotFoundException if no registration is found with the given ID.
     */
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
