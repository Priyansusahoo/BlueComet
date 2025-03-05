package com.bluecomet.event_planner.controller;

import com.bluecomet.event_planner.dto.registration.EventRegistrationRequest;
import com.bluecomet.event_planner.dto.registration.EventRegistrationResponse;
import com.bluecomet.event_planner.enums.RegistrationStatus;
import com.bluecomet.event_planner.service.event_registration.EventRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event-registration")
@RequiredArgsConstructor
@Validated
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    @PostMapping
    public ResponseEntity<EventRegistrationResponse> registerUserForEvent(
            @Valid @RequestBody EventRegistrationRequest request)
    {
        return ResponseEntity.ok(eventRegistrationService.registerUserForEvent(request));
    }

    @DeleteMapping("/{userId}/{eventId}")
    public ResponseEntity<String> cancelRegistration(
            @PathVariable Long userId,
            @PathVariable Long eventId)
    {
        eventRegistrationService.cancelRegistration(userId, eventId);
        return ResponseEntity.ok("Registration cancelled successfully.");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EventRegistrationResponse>> getUserRegistrations(
            @PathVariable Long userId)
    {
        return ResponseEntity.ok(eventRegistrationService.getRegistrationsByUser(userId));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<EventRegistrationResponse>> getEventRegistrations(
            @PathVariable Long eventId)
    {
        return ResponseEntity.ok(eventRegistrationService.getRegistrationsByEvent(eventId));
    }

    @PutMapping("/{registrationId}/status")
    public ResponseEntity<String> updateRegistrationStatus(
            @PathVariable Long registrationId,
            @RequestParam String newStatus)
    {
        RegistrationStatus status = RegistrationStatus.fromString(newStatus);
        eventRegistrationService.updateRegistrationStatus(registrationId, status);
        return ResponseEntity.ok("Registration status updated successfully.");
    }

}
