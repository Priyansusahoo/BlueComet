package com.bluecomet.event_planner.utils.mapper;

import com.bluecomet.event_planner.dto.event.EventRequest;
import com.bluecomet.event_planner.dto.event.EventResponse;
import com.bluecomet.event_planner.entity.Event;
import com.bluecomet.event_planner.utils.customexceptions.EventNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EventMapper {

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
