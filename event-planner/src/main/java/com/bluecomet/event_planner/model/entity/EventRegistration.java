package com.bluecomet.event_planner.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.bluecomet.event_planner.model.vo.RegistrationStatus;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Entity representing an event registration.
 *
 * @author Priyansu
 */
@Entity
@Table(name = "event_registrations")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EventRegistration implements Serializable {

    @Serial
    private static final long serialVersionUID = -846646922207589122L;

    @Version
    private int version;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /* The user ID of the registrant.
     * User ID will be generated from User services */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(STRING)
    @Column(name = "registration_status", nullable = false, length = 20)
    private RegistrationStatus registrationStatus;

    @Column(name = "registered_at", nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    /* Auto-sets the registeredAt timestamp before persisting the entity. */
    @PrePersist
    protected void onRegister() {
        this.registeredAt = LocalDateTime.now();
    }

}
