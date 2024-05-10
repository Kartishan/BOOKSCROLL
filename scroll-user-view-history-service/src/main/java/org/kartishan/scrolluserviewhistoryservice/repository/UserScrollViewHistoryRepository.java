package org.kartishan.scrolluserviewhistoryservice.repository;

import org.kartishan.scrolluserviewhistoryservice.model.UserScrollViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserScrollViewHistoryRepository extends JpaRepository<UserScrollViewHistory, UUID> {

    List<UserScrollViewHistory> findAllByScroll_Id(UUID scrollId);

    List<UserScrollViewHistory> findAllByUser_Id(UUID userId);

    Optional<UserScrollViewHistory> findByUserIdandScrollId(UUID userId, UUID bookId);
}
