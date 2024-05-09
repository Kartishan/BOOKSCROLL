package org.kartishan.viewhistory.service;

import lombok.AllArgsConstructor;
import org.kartishan.viewhistory.model.UserBookViewHistory;
import org.kartishan.viewhistory.model.dto.BookDTO;
import org.kartishan.viewhistory.model.dto.BookViewHistoryDTO;
import org.kartishan.viewhistory.repository.UserBookViewHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserBookViewHistoryService {
    private final UserBookViewHistoryRepository userBookViewHistoryRepository;
    private final BookServiceClient bookServiceClient;

    public void saveUserBookViewHistory(UUID userId, UUID bookId) {
        UserBookViewHistory history = userBookViewHistoryRepository.findByUserIdAndBookId(userId, bookId)
                .orElseGet(() -> {
                    UserBookViewHistory newUserBookViewHistory = new UserBookViewHistory();
                    newUserBookViewHistory.setUserId(userId);
                    newUserBookViewHistory.setBookId(bookId);
                    return newUserBookViewHistory;
                });

        history.setViewTime(new Date());
        userBookViewHistoryRepository.save(history);
    }

    public List<BookViewHistoryDTO> getUserHistory(UUID userId) {
        List<UserBookViewHistory> histories = userBookViewHistoryRepository.findByUserId(userId);
        List<UUID> bookIds = getDistinctBookIds(histories);
        List<BookDTO> books = bookServiceClient.getBooksByIds(bookIds);
        return createBookViewHistoryDTOList(histories, books);
    }

    private List<UUID> getDistinctBookIds(List<UserBookViewHistory> histories) {
        return histories.stream()
                .map(UserBookViewHistory::getBookId)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<BookViewHistoryDTO> createBookViewHistoryDTOList(List<UserBookViewHistory> histories, List<BookDTO> books) {
        Map<UUID, Date> viewTimes = getViewTimesMap(histories);
        return books.stream()
                .map(book -> createBookViewHistoryDTO(book, viewTimes))
                .collect(Collectors.toList());
    }

    private Map<UUID, Date> getViewTimesMap(List<UserBookViewHistory> histories) {
        return histories.stream()
                .collect(Collectors.toMap(UserBookViewHistory::getBookId, UserBookViewHistory::getViewTime));
    }

    private BookViewHistoryDTO createBookViewHistoryDTO(BookDTO book, Map<UUID, Date> viewTimes) {
        return new BookViewHistoryDTO(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                book.getAuthorFull(),
                book.getDescription(),
                book.getRating(),
                book.getPageCount(),
                book.getCategories(),
                viewTimes.get(book.getId())
        );
    }

}
