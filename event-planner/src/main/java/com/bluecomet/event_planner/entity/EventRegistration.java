package com.bluecomet.event_planner.entity;

import com.bluecomet.event_planner.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
/**
 * Entity representing an event registration.
 *
 * @author Priyansu
 */
@Entity
@Table(name = "event_registrations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /** The user ID of the registrant.
     * User ID will be generated from User services */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_status", nullable = false, length = 20)
    private RegistrationStatus registrationStatus;

    @Column(name = "registered_at", nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    /** Auto-sets the registeredAt timestamp before persisting the entity. */
    @PrePersist
    protected void onRegister() {
        this.registeredAt = LocalDateTime.now();
    }

    /** Optimistic locking version field. */
    @Version
    private int version;
}
