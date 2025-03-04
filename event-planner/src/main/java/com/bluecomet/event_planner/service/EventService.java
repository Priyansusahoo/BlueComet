package com.bluecomet.event_planner.service;

import com.bluecomet.event_planner.dto.event.EventRequest;
import com.bluecomet.event_planner.dto.event.EventResponse;
import com.bluecomet.event_planner.entity.Event;
import com.bluecomet.event_planner.enums.EventStatus;
import com.bluecomet.event_planner.repository.EventRepository;
import com.bluecomet.event_planner.utils.customexceptions.EventAlreadyCancelledException;
import com.bluecomet.event_planner.utils.customexceptions.EventNotFoundException;
import com.bluecomet.event_planner.utils.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
/**
 * @author Priyansu
 */
@Slf4j
@Service("eventService")
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    /**
     * Fetches a paginated list of events sorted by event date in descending order.
     *
     * @param page the page number (0-based index)
     * @param size the number of events per page
     * @return a Page containing {@link EventResponse}
     */
    public Page<EventResponse> getAllEvents(int page, int size) {
        log.info("Fetching events - Page: {}, Size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("eventDateTime").descending());

        return eventRepository.findAll(pageable).map(eventMapper::toResponse);
    }

    /**
     * Fetches an event by its ID.
     *
     * @param id the unique identifier of the event
     * @return the corresponding {@link EventResponse} DTO
     * @throws EventNotFoundException if the event is not found
     */
    public EventResponse getEventById(Long id) {
        log.info("Fetching event with ID: {}", id);
        return eventMapper.toResponse(findEventById(id));
    }

    /**
     * Processes the creation of a new event {@link Event}.
     *
     * @param eventRequest the event request data
     * @return the created {@link Event} converted to {@link EventResponse} DTO
     */
    @Transactional
    public EventResponse createEvent(EventRequest eventRequest) {
        log.info("Processing event creation: {}", eventRequest);
        Event event = eventMapper.toEntity(eventRequest);
        return eventMapper.toResponse(eventRepository.save(event));
    }

    /**
     * Updates an existing event {@link Event} in the database.
     *
     * @param id                  the ID of the event to update
     * @param updatedEventRequest the new event details
     * @return the updated event response DTO {@link EventResponse}
     * @throws EventNotFoundException if the event is not found
     */
    @Transactional
    public EventResponse updateEvent(Long id, EventRequest updatedEventRequest) {
        log.info("Updating event with ID: {}", id);

        Event event = findEventById(id);
        eventMapper.updateEntity(event, updatedEventRequest);
        return eventMapper.toResponse(eventRepository.save(event));
    }

    /**
     * Deletes an event {@link Event} by its ID.
     *
     * @param id the ID of the event to delete
     * @throws EventNotFoundException if the event does not exist
     */
    @Transactional
    public void deleteEvent(Long id) {
        log.info("Deleting event with ID: {}", id);
        Event event = findEventById(id);
        eventRepository.deleteById(id);
        log.info("Event with ID: {} deleted successfully", id);
    }

    /**
     * Retrieves a list of events filtered by the provided status.
     *
     * @param status the event status
     * @return a list of event responses DTO {@link EventResponse} matching the status
     */
    public List<EventResponse> getEventsByStatus(EventStatus status) {
        log.info("Fetching events with status: {}", status);
        return eventRepository.findByStatus(status).stream().map(eventMapper::toResponse).toList();
    }

    /**
     * Cancels an event by marking its status as CANCELLED.
     *
     * @param id the event ID
     * @return the updated event response {@link EventResponse}
     * @throws EventAlreadyCancelledException if the event is already cancelled
     */
    @Transactional
    public EventResponse cancelEvent(Long id) {
        log.info("Attempting to cancel event with ID: {}", id);

        Event event = findEventById(id);

        if (event.getStatus() == EventStatus.CANCELLED) {
            log.warn("Event with ID: {} is already cancelled", id);
            throw new EventAlreadyCancelledException("Event with ID: " + id + " is already cancelled");
        }
        event.setStatus(EventStatus.CANCELLED);
        event.setUpdatedAt(LocalDateTime.now());

        return eventMapper.toResponse(eventRepository.save(event));
    }

    /**
     * Fetches events occurring within the given date range.
     *
     * @param start the start date-time of the range.
     * @param end the end date-time of the range.
     * @return a list of {@link EventResponse} containing event details.
     */
    public List<EventResponse> getEventsByDateTimeRange(LocalDateTime start, LocalDateTime end) {
        log.info("Fetching events between {} and {}", start, end);
        return eventRepository.findByEventDateTimeBetween(start, end)
                .stream()
                .map(eventMapper::toResponse)
                .toList();
    }

    /**
     * Finds an event by ID in the repository.
     *
     * @param id the event ID to search for
     * @return the found {@link Event} entity
     * @throws EventNotFoundException if the event does not exist
     */
    private Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event with ID: " + id + " not found"));
    }
}
