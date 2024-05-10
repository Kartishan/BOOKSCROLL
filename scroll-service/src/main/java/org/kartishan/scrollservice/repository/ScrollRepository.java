package org.kartishan.scrollservice.repository;

import org.kartishan.scrollservice.model.Scroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ScrollRepository extends JpaRepository<Scroll, UUID> {
    Optional<Scroll> findById(UUID id);
}

