package com.bluecomet.event_planner.controller;

import com.bluecomet.event_planner.dto.event.EventRequest;
import com.bluecomet.event_planner.dto.event.EventResponse;
import com.bluecomet.event_planner.dto.request.EventDateRangeRequest;
import com.bluecomet.event_planner.enums.EventStatus;
import com.bluecomet.event_planner.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author Priyansu
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Tag(name = "Event Management", description = "Event Management API")
public class EventController {

    private final EventService eventService;

    @GetMapping
    @Operation(summary = "Get all events", description = "Retrieve a paginated list of all events")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of events fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"),
            @ApiResponse(responseCode = "204", description = "No events found")
    })
    public ResponseEntity<Page<EventResponse>> getAllEvents(
            @Parameter(description = "Page number (0-based index)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of events per page", example = "10") @RequestParam(defaultValue = "10") int size)
    {
        log.info("API Call: GET /api/v1/events?page={}&size={} - Fetching paginated events", page, size);
        Page<EventResponse> events = eventService.getAllEvents(page, size);
        return events.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an event by ID", description = "Retrieve an event using its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found exception")
    })
    public ResponseEntity<EventResponse> getEventById(
            @Parameter(description = "ID of the event to retrieve", example = "1") @PathVariable Long id)
    {
        log.info("API Call: GET /api/v1/events/{} - Fetching event details", id);
        EventResponse event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping()
    @Operation(summary = "Create a new event", description = "Create a new event with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid event details")
    })
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest eventRequest)
    {
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
    @Operation(summary = "Update an existing event", description = "Update an existing event using its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid event details"),
            @ApiResponse(responseCode = "404", description = "Event not found exception")
    })
    public ResponseEntity<EventResponse> updateEvent(
            @Parameter(description = "ID of the event to update", example = "1") @PathVariable Long id,
            @Valid @RequestBody EventRequest eventRequest)
    {
        log.info("API Call: PUT /api/v1/events/{} - Updating Event: {}", id, eventRequest);
        return ResponseEntity.ok(eventService.updateEvent(id, eventRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an event", description = "Delete an event using its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found exception")
    })
    public ResponseEntity<Void> deleteEvent(
            @Parameter(description = "ID of the event to delete", example = "1") @PathVariable Long id)
    {
        log.info("API Call: DELETE /api/v1/events/{} - Deleting event", id);
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    @Operation(
            summary = "Get events by status",
            description = "Retrieve a list of events filtered by their status (e.g., UPCOMING, ONGOING, COMPLETED, CANCELLED)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Events fetched successfully",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = EventResponse.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid event status")
    })
    public ResponseEntity<List<EventResponse>> getEventsByStatus(
            @Parameter(description = "Event status to filter by", example = "UPCOMING") @RequestParam String status)
    {
        log.info("API Call: GET /api/v1/events/status?status={} - Fetching events by status", status);
        EventStatus eventStatus = EventStatus.fromString(status);
        List<EventResponse> events = eventService.getEventsByStatus(eventStatus);
        return events.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(events);
    }

    @PutMapping("/{id}/cancel")
    @Operation(
            summary = "Cancel an event",
            description = "Marks an event as cancelled based on its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Event already cancelled"),
            @ApiResponse(responseCode = "404", description = "Event not found exception")
    })
    public ResponseEntity<EventResponse> cancelEvent(
            @Parameter(description = "ID of the event to cancel", example = "1") @PathVariable Long id)
    {
        log.info("API Call: PUT /api/v1/events/{}/cancel - Cancelling event", id);
        EventResponse cancelledEventResponse = eventService.cancelEvent(id);
        return ResponseEntity.ok(cancelledEventResponse);
    }

    @PostMapping("/between")
    @Operation(
            summary = "Get events within a date range",
            description = "Retrieve a list of events that fall within the specified date range"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Events fetched successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = EventResponse.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid date range format"),
            @ApiResponse(responseCode = "204", description = "No events found")
    })
    public ResponseEntity<List<EventResponse>> getEventsBetweenDates(@Valid @RequestBody EventDateRangeRequest request)
    {
        log.info("API Call: GET /api/v1/events/between - Fetching events from {} to {}", request.start(), request.end());
        request.validate();
        List<EventResponse> events = eventService.getEventsByDateTimeRange(request.start(), request.end());
        return events.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(events);
    }
}
