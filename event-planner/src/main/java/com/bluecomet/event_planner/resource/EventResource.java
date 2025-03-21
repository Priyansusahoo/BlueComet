package com.bluecomet.event_planner.resource;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.bluecomet.event_planner.model.exchange.EventRequest;
import com.bluecomet.event_planner.model.exchange.EventResponse;
import com.bluecomet.event_planner.model.entity.Event;
import com.bluecomet.event_planner.model.vo.EventStatus;
import com.bluecomet.event_planner.service.impl.EventService;
import com.bluecomet.event_planner.utils.DateTimeUtils;
import com.bluecomet.event_planner.exception.EventNotFoundException;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Priyansu
 */
@RestController
@RequestMapping(
    path = "/v1/events",
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE
)
@Tag(name = "Event Management", description = "Event Management API")
@RequiredArgsConstructor
@Slf4j
public class EventResource {

    private final EventService eventService;

    /**
     * Retrieves a paginated list of all events.
     *
     * @param page the page number (0-based index)
     * @param size the number of events per page
     * @return a paginated list of events or 204 No Content if no events exist
     */
    @GetMapping
    @Operation(summary = "Get all events", description = "Retrieve a paginated list of all events")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of events fetched successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"),
        @ApiResponse(responseCode = "204", description = "No events found")
    })
    public ResponseEntity<Page<EventResponse>> getAllEvents(
        @Parameter(description = "Page number (0-based index)", example = "0") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Number of events per page", example = "10") @RequestParam(defaultValue = "10") int size) {
        log.info("API Call: GET /api/v1/events?page={}&size={} - Fetching paginated events", page, size);

        Page<EventResponse> events = eventService.getAllEvents(page, size);
        return events.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(events);

    }

    /**
     * Retrieves an event by its unique identifier.
     *
     * @param id the unique ID of the event to retrieve
     * @return the {@link Event} details, converted to {@link EventResponse} if found
     * @throws EventNotFoundException if no event is found with the given ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get an event by ID", description = "Retrieve an event using its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Event fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Event not found exception")
    })
    public ResponseEntity<EventResponse> getEventById(
        @Parameter(description = "ID of the event to retrieve", example = "1") @PathVariable Long id) {
        log.info("API Call: GET /api/v1/events/{} - Fetching event details", id);
        EventResponse event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    /**
     * Creates a new event {@link Event} with the provided details.
     *
     * @param eventRequest the request body containing event details
     * @return the created event converted to {@link EventResponse} with a location header
     */
    @PostMapping()
    @Operation(summary = "Create a new event", description = "Create a new event with the provided details")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Event created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid event details")
    })
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        log.info("API Call: POST /api/v1/events - Creating Event: {}", eventRequest);
        EventResponse createdEvent = eventService.createEvent(eventRequest);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdEvent.id())
            .toUri();

        return ResponseEntity.created(location).body(createdEvent);
    }

    /**
     * Updates an existing event {@link Event} using its ID.
     *
     * @param id           the ID of the event to update
     * @param eventRequest the updated event details
     * @return the updated event response {@link EventResponse}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing event", description = "Update an existing event using its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Event updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid event details"),
        @ApiResponse(responseCode = "404", description = "Event not found exception")
    })
    public ResponseEntity<EventResponse> updateEvent(
        @Parameter(description = "ID of the event to update", example = "1") @PathVariable Long id,
        @Valid @RequestBody EventRequest eventRequest) {
        log.info("API Call: PUT /api/v1/events/{} - Updating Event: {}", id, eventRequest);
        return ResponseEntity.ok(eventService.updateEvent(id, eventRequest));
    }

    /**
     * Deletes an event {@link Event} using its ID.
     *
     * @param id the ID of the event to delete
     * @return a response with no content if deletion is successful
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an event", description = "Delete an event using its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Event not found exception")
    })
    public ResponseEntity<Void> deleteEvent(
        @Parameter(description = "ID of the event to delete", example = "1") @PathVariable Long id) {
        log.info("API Call: DELETE /api/v1/events/{} - Deleting event", id);
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a list of events filtered by their status {@link EventStatus}.
     *
     * @param status the event status (e.g., UPCOMING, ONGOING, COMPLETED, CANCELLED)
     * @return a list of events matching the given status
     */
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
        @Parameter(name = "status", description = "The new status of the Event",
            required = true, schema = @Schema(
            description = "Event status must be one of the allowed values",
            implementation = EventStatus.class))
        @RequestParam String status) {
        log.info("API Call: GET /api/v1/events/status?status={} - Fetching events by status", status);
        EventStatus eventStatus = EventStatus.fromString(status);
        List<EventResponse> events = eventService.getEventsByStatus(eventStatus);
        return events.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(events);
    }

    /**
     * Cancels an event {@link Event} based on its ID.
     *
     * @param id the ID of the event to cancel
     * @return the updated event response DTO {@link EventResponse}
     */
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
        @Parameter(description = "ID of the event to cancel", example = "1") @PathVariable Long id) {
        log.info("API Call: PUT /api/v1/events/{}/cancel - Cancelling event", id);
        EventResponse cancelledEventResponse = eventService.cancelEvent(id);
        return ResponseEntity.ok(cancelledEventResponse);
    }

    /**
     * Retrieves events that fall within the specified date range.
     * <p>
     * This API fetches events occurring between the provided start and end timestamps.
     * If no events are found, it returns a 204 No Content response.
     * </p>
     *
     * @param start the field containing the start date.
     * @param end   the field containing the end date.
     * @return {@link ResponseEntity} containing a list of {@link EventResponse} if events are found.
     * Returns 204 No Content if no events exist in the given range.
     * @throws IllegalArgumentException if the start date is after the end date.
     */
    @GetMapping("/between")
    @Operation(
        summary = "Get events within a date range",
        description = "Retrieve a list of events that fall within the specified date range"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Events fetched successfully",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(
                    schema = @Schema(implementation = EventResponse.class)
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid date range format"),
        @ApiResponse(responseCode = "204", description = "No events found")
    })
    public ResponseEntity<List<EventResponse>> getEventsBetweenDates(
        @RequestParam
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime start,

        @RequestParam
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime end
    ) {
        log.info("API Call: GET /api/v1/events/between - Fetching events from {} to {}", start, end);

        if (!DateTimeUtils.validateStartAndEndDT(start, end))
            throw new IllegalArgumentException("Invalid date range format");
        //TODO: return ResponseEntity.unprocessableEntity().body("Invalid date range format");

        List<EventResponse> events = eventService.getEventsByDateTimeRange(start, end);
        return events.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(events);
    }
}
