package org.kartishan.viewhistory.repository;

import org.kartishan.viewhistory.model.UserBookViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBookViewHistoryRepository extends JpaRepository<UserBookViewHistory, Long> {

}
