package com.bluecomet.event_planner.controller;

import com.bluecomet.event_planner.dto.event.EventRequest;
import com.bluecomet.event_planner.dto.event.EventResponse;
import com.bluecomet.event_planner.dto.request.EventDateRangeRequest;
import com.bluecomet.event_planner.enums.EventStatus;
import com.bluecomet.event_planner.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventResponse>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("API Call: GET /api/v1/events?page={}&size={} - Fetching paginated events", page, size);

        Page<EventResponse> events = eventService.getAllEvents(page, size);

        return events.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        log.info("API Call: GET /api/v1/events/{} - Fetching event details", id);
        EventResponse event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping()
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        log.info("API Call: POST /api/v1/events - Creating Event: {}", eventRequest);
        EventResponse createdEvent = eventService.createEvent(eventRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdEvent.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequest eventRequest) {
        log.info("API Call: PUT /api/v1/events/{} - Updating Event: {}", id, eventRequest);
        return ResponseEntity.ok(eventService.updateEvent(id, eventRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        log.info("API Call: DELETE /api/v1/events/{} - Deleting event", id);
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<EventResponse>> getEventsByStatus(@RequestParam String status) {
        log.info("API Call: GET /api/v1/events/status?status={} - Fetching events by status", status);

        EventStatus eventStatus = EventStatus.fromString(status);
        return ResponseEntity.ok(eventService.getEventsByStatus(eventStatus));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<EventResponse> cancelEvent(@PathVariable Long id) {
        log.info("API Call: PUT /api/events/{}/cancel - Cancelling event", id);
        EventResponse cancelledEventResponse = eventService.cancelEvent(id);
        return ResponseEntity.ok(cancelledEventResponse);
    }

    /**
     * Example API call:
     * GET http://localhost:8080/api/events/between?start=2025-03-01T10:00:00&end=2025-03-10T18:00:00
     */
    @GetMapping("/between")
    public ResponseEntity<List<EventResponse>> getEventsBetweenDates(@Valid EventDateRangeRequest request) {

        log.info("API Call: GET /api/events/between - Fetching events from {} to {}", request.start(), request.end());
        request.validate();
        return ResponseEntity.ok(eventService.getEventsByDateTimeRange(request.start(), request.end()));
    }

}
