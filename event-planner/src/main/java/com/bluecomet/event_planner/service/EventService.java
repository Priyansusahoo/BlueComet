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

    public Page<EventResponse> getAllEvents(int page, int size) {
        log.info("Fetching events - Page: {}, Size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("eventDateTime").descending());

        return eventRepository.findAll(pageable).map(eventMapper::toResponse);
    }

    public EventResponse getEventById(Long id) {
        log.info("Fetching event with ID: {}", id);
        return eventMapper.toResponse(findEventById(id));
    }

    @Transactional
    public EventResponse createEvent(EventRequest eventRequest) {
        log.info("Processing event creation: {}", eventRequest);
        Event event = eventMapper.toEntity(eventRequest);
        return eventMapper.toResponse(eventRepository.save(event));
    }

    @Transactional
    public EventResponse updateEvent(Long id, EventRequest updatedEventRequest) {
        log.info("Updating event with ID: {}", id);

        Event event = findEventById(id);
        eventMapper.updateEntity(event, updatedEventRequest);
        return eventMapper.toResponse(eventRepository.save(event));
    }

    @Transactional
    public void deleteEvent(Long id) {
        log.info("Deleting event with ID: {}", id);
        Event event = findEventById(id);
        eventRepository.deleteById(id);
        log.info("Event with ID: {} deleted successfully", id);
    }

    public List<EventResponse> getEventsByStatus(EventStatus status) {
        log.info("Fetching events with status: {}", status);
        return eventRepository.findByStatus(status).stream().map(eventMapper::toResponse).toList();
    }

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

    public List<EventResponse> getEventsByDateTimeRange(LocalDateTime start, LocalDateTime end) {
        log.info("Fetching events between {} and {}", start, end);
        return eventRepository.findByEventDateTimeBetween(start, end)
                .stream()
                .map(eventMapper::toResponse)
                .toList();
    }

    private Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event with ID: " + id + " not found"));
    }
}
