package org.kartishan.scrolluserviewhistoryservice.controller;

import lombok.RequiredArgsConstructor;
import org.kartishan.scrolluserviewhistoryservice.model.UserScrollViewHistory;
import org.kartishan.scrolluserviewhistoryservice.service.UserScrollViewHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-scroll-view-history")
public class UserScrollViewHistoryController {
    private final UserScrollViewHistoryService userScrollViewHistoryService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveViewHistory(@RequestParam UUID userId, @RequestParam UUID scrollId) {
        userScrollViewHistoryService.saveUserScrollViewHistory(userId, scrollId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UUID>> getUserHistory(@PathVariable UUID userId) {
        List<UserScrollViewHistory> history = userScrollViewHistoryService.getUserHistory(userId);
        List<UUID> scrollIds = history.stream()
                .map(UserScrollViewHistory::getScrollId)
                .collect(Collectors.toList());
        return ResponseEntity.ok(scrollIds);
    }
}
