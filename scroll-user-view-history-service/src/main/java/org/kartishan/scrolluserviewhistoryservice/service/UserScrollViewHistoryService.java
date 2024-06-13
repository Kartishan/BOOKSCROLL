package org.kartishan.scrolluserviewhistoryservice.service;

import lombok.AllArgsConstructor;
import org.kartishan.scrolluserviewhistoryservice.model.UserScrollViewHistory;
import org.kartishan.scrolluserviewhistoryservice.repository.UserScrollViewHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserScrollViewHistoryService {
    private final UserScrollViewHistoryRepository userScrollViewHistoryRepository;

    public void saveUserScrollViewHistory(UUID userId, UUID scrollId) {
        UserScrollViewHistory userScrollViewHistory = userScrollViewHistoryRepository.findByUserIdAndScrollId(userId, scrollId)
                .orElseGet(() -> {
                    UserScrollViewHistory newUserScrollViewHistory = new UserScrollViewHistory();
                    newUserScrollViewHistory.setUserId(userId);
                    newUserScrollViewHistory.setScrollId(scrollId);
                    return newUserScrollViewHistory;
                });
        userScrollViewHistory.setViewTime(new Date());
        userScrollViewHistoryRepository.save(userScrollViewHistory);
    }

    public List<UserScrollViewHistory> getUserHistory(UUID userId) {
        return userScrollViewHistoryRepository.findAllByUserId(userId);
    }
    public List<UUID> getAllUsers() {
        return userScrollViewHistoryRepository.findAllUsers();
    }
}
