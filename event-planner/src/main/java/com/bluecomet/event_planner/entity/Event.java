package com.bluecomet.event_planner.entity;

import com.bluecomet.event_planner.enums.EventStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
/**
 * Entity representing an event in the system.
 * <p>
 * This class maps to the "events" table in the database and stores
 * details about various events.
 * </p>
 *
 * <p>
 * The entity includes fields for event name, description, location,
 * date, status, and timestamps for creation and updates.
 * </p>
 *
 * @author Priyansu
 */
@Entity
@Table(name = "events", indexes = {@Index(name = "idx_event_name", columnList = "name")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    @Size(max = 255, message = "Name must be under 255 characters")
    @NotBlank(message = "Name is required")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotBlank(message = "Location cannot be empty")
    @Column(nullable = false, length = 200)
    private String location;

    @NotNull(message = "Event date and time cannot be null")
    @Future(message = "Event date must be in the future")
    @Column(nullable = false)
    private LocalDateTime eventDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EventStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * The version field used for optimistic locking.
     * <p>
     * Helps prevent concurrent update conflicts.
     * </p>
     */
    @Version
    private int version;
}
