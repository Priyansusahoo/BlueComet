package com.bluecomet.event_planner.dto.event;

import com.bluecomet.event_planner.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
