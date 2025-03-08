package com.bluecomet.event_planner.controller;

import com.bluecomet.event_planner.dto.registration.EventRegistrationRequest;
import com.bluecomet.event_planner.dto.registration.EventRegistrationResponse;
import com.bluecomet.event_planner.enums.RegistrationStatus;
import com.bluecomet.event_planner.service.event_registration.EventRegistrationService;
import com.bluecomet.event_planner.utils.api.ApiErrorResponse;
import com.bluecomet.event_planner.utils.customexceptions.event_registration.RegistrationNotFoundException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller for handling event registration-related operations.
 *
 * @author Priyansu
 */
@RestController
@RequestMapping("/api/v1/event-registration")
@RequiredArgsConstructor
@Validated
@Tag(name = "Event Registration", description = "Endpoints for registration of users for events")
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    /**
     * Registers a user for an event.
     *
     * @param request The registration request containing user ID and event ID.
     * @return ResponseEntity containing the event registration response.
     */
    @Operation(
            summary = "Register user for event",
            description = "Register a user for an event by providing the user ID and event ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully registered",
                    content = @Content(schema = @Schema(implementation = EventRegistrationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error or duplicate registration",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<EventRegistrationResponse> registerUserForEvent(
            @Valid @RequestBody @Parameter(description = "User and event details for registration") EventRegistrationRequest request)
    {
        return ResponseEntity.ok(eventRegistrationService.registerUserForEvent(request));
    }

    /**
     * Cancels a user's registration for a given event.
     *
     * @param userId  The ID of the user whose registration is to be canceled.
     * @param eventId The ID of the event from which the user wants to unregister.
     * @return A response indicating the cancellation status.
     */
    @Operation(
            summary = "Cancel event registration",
            description = "Cancel a user's registration for an event by providing the user ID and event ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registration cancelled successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Registration is already cancelled",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Registration not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{userId}/{eventId}")
    public ResponseEntity<String> cancelRegistration(
            @PathVariable Long userId,
            @PathVariable Long eventId)
    {
        eventRegistrationService.cancelRegistration(userId, eventId);
        return ResponseEntity.ok("Registration cancelled successfully.");
    }

    /**
     * Retrieves all event registrations for a given user.
     *
     * @param userId the ID of the user whose registrations are to be retrieved
     * @return a ResponseEntity containing a list of event registrations {@link EventRegistrationResponse}
     */
    @Operation(summary = "Get user registrations",
            description = "Retrieves all event registrations for a given user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of event registrations retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventRegistrationResponse.class)))),
            @ApiResponse(responseCode = "404", description = "User has no registrations",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EventRegistrationResponse>> getUserRegistrations(
            @PathVariable Long userId)
    {
        return ResponseEntity.ok(eventRegistrationService.getRegistrationsByUser(userId));
    }

    /**
     * Retrieves all user registrations for a given event.
     *
     * @param eventId The ID of the event.
     * @return List of event registrations.
     */
    @Operation(summary = "Get event registrations",
            description = "Retrieves all user registrations for a given event. Returns an empty list if no registrations exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of registrations retrieved successfully (empty list if no registrations found)",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventRegistrationResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<EventRegistrationResponse>> getEventRegistrations(
            @PathVariable Long eventId)
    {
        return ResponseEntity.ok(eventRegistrationService.getRegistrationsByEvent(eventId));
    }

    /**
     * Updates the status of a specific event registration.
     * If the status is already set to the provided value, no update occurs.
     *
     * @param registrationId The unique ID of the event registration to update.
     * @param newStatus The new status to assign to the registration.
     * @return ResponseEntity containing a success message.
     * @throws RegistrationNotFoundException if the registration ID is not found.
     * @throws IllegalArgumentException if an invalid registration status is provided.
     */
    @Operation(summary = "Update registration status",
            description = "Updates the status of a specific event registration. If the status is already set, no update occurs.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registration status updated successfully or no change needed"),
            @ApiResponse(responseCode = "400", description = "Invalid registration status provided",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Registration not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{registrationId}/status")
    public ResponseEntity<String> updateRegistrationStatus(
            @PathVariable Long registrationId,
            @Parameter(name = "newStatus", description = "The new status of the registration",
                    required = true, schema = @Schema(
                            description = "Registration status must be one of the allowed values",
                            implementation = RegistrationStatus.class))
            @RequestParam String newStatus)
    {
        RegistrationStatus status = RegistrationStatus.fromString(newStatus);
        eventRegistrationService.updateRegistrationStatus(registrationId, status);
        return ResponseEntity.ok("Registration status updated successfully or no change needed.");
    }
}
