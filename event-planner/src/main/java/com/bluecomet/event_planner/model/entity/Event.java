package com.bluecomet.event_planner.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.bluecomet.event_planner.model.vo.EventStatus;


import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

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
@Table(
    name = "events",
    indexes = {
        @Index(name = "idx_event_name", columnList = "name")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event implements Serializable {

    @Serial
    private static final long serialVersionUID = 2105163062528727519L;

    @Version
    private int version;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(nullable = false, length = 200)
    private String location;

    @NotNull(message = "Event date and time cannot be null")
    @Column(name = "event_datetime", nullable = false)
    private LocalDateTime eventDateTime;

    @Enumerated(STRING)
    @Column(nullable = false, length = 20)
    private EventStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    {
        this.createdAt = LocalDateTime.now();
    }
}
