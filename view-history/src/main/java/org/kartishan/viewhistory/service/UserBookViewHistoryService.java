package org.kartishan.viewhistory.service;

import lombok.AllArgsConstructor;
import org.kartishan.viewhistory.model.UserBookViewHistory;
import org.kartishan.viewhistory.repository.UserBookViewHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class UserBookViewHistoryService {
    private final UserBookViewHistoryRepository userBookViewHistoryRepository;

    public void saveUserBookViewHistory(Long userId, Long bookId) {
        UserBookViewHistory history = new UserBookViewHistory();
        history.setUserId(userId);
        history.setBookId(bookId);
        history.setViewTime(new Date());

        userBookViewHistoryRepository.save(history);
    }
}
