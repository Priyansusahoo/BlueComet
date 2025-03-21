package com.bluecomet.event_planner.model.exchange;

import com.bluecomet.event_planner.model.vo.EventStatus;

import java.time.LocalDateTime;
/**
 * DTO for responding with event details.
 * <p>
 * This class is used to send event data to clients after retrieval
 * from the database.
 * </p>
 *
 * @author Priyansu
 */
public record EventResponse(
    Long id,
    String name,
    String location,
    LocalDateTime eventDateTime,
    String description,
    EventStatus status
) {}
