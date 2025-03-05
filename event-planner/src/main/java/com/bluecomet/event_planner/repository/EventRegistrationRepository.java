package com.bluecomet.event_planner.repository;

import com.bluecomet.event_planner.entity.EventRegistration;
import com.bluecomet.event_planner.enums.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {

    List<EventRegistration> findByEventId(Long eventId);

    List<EventRegistration> findByUserId(Long userId);

    Optional<EventRegistration> findByUserIdAndEventId(Long userId, Long eventId);

    Boolean existsByUserIdAndEventId(Long userId, Long eventId);
}
