package com.bluecomet.event_planner.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.bluecomet.event_planner.model.exchange.EventRequest;
import com.bluecomet.event_planner.model.exchange.EventResponse;
import com.bluecomet.event_planner.model.entity.Event;
import com.bluecomet.event_planner.exception.EventNotFoundException;

/**
 * @author Priyansu
 */
@Component
public class EventMapper {

    /**
     * Converts an {@link Event} entity to an {@link EventResponse} DTO.
     *
     * @param event the Event entity
     * @return the corresponding {@link EventResponse} DTO
     * @throws EventNotFoundException if the event is null
     */
    public EventResponse toResponse(Event event) {
        return Optional.ofNullable(event)
                .map(e -> new EventResponse(
                        e.getId(),
                        e.getName(),
                        e.getLocation(),
                        e.getEventDateTime(),
                        e.getDescription(),
                        e.getStatus()
                ))
                .orElseThrow(() -> new EventNotFoundException("Event cannot be null"));
    }

    public List<EventResponse> toResponseList(List<Event> events) {
        return events.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Converts an {@link EventRequest} DTO to an {@link Event} entity.
     *
     * @param eventRequest the event request DTO
     * @return the corresponding Event entity
     */
    public Event toEntity(EventRequest eventRequest) {
        if (eventRequest == null) {
            return null;
        }

        Event event = new Event();
        event.setName(eventRequest.getName());
        event.setLocation(eventRequest.getLocation());
        event.setDescription(eventRequest.getDescription());
        event.setEventDateTime(eventRequest.getEventDateTime());
        event.setStatus(eventRequest.getStatus());

        return event;
    }

    /**
     * Updates an existing event {@link Event} entity with new details.
     *
     * @param event               the existing event entity
     * @param updatedEventRequest the updated event request data
     */
    public void updateEntity(Event event, EventRequest updatedEventRequest) {
        if (event == null || updatedEventRequest == null) {
            return;
        }

        event.setName(updatedEventRequest.getName());
        event.setLocation(updatedEventRequest.getLocation());
        event.setDescription(updatedEventRequest.getDescription());
        event.setEventDateTime(updatedEventRequest.getEventDateTime());
        event.setStatus(updatedEventRequest.getStatus());
    }

}
