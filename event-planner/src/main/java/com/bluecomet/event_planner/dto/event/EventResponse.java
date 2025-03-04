package com.bluecomet.event_planner.dto.event;

import com.bluecomet.event_planner.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private Long id;
    private String name;
    private String location;
    private LocalDateTime eventDateTime;
    private String description;
    private EventStatus status;
}
