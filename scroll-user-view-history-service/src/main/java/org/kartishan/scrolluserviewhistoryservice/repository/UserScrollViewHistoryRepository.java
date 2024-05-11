package org.kartishan.scrolluserviewhistoryservice.repository;

import org.kartishan.scrolluserviewhistoryservice.model.UserScrollViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserScrollViewHistoryRepository extends JpaRepository<UserScrollViewHistory, UUID> {

    List<UserScrollViewHistory> findAllByUserId(UUID userId);

    Optional<UserScrollViewHistory> findByUserIdAndScrollId(UUID userId, UUID scrollId);
}
