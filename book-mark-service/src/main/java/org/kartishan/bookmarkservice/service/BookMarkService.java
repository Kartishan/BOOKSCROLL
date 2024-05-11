package org.kartishan.bookmarkservice.service;

import lombok.RequiredArgsConstructor;
import org.kartishan.bookmarkservice.model.BookmarkDocument;
import org.kartishan.bookmarkservice.model.BookmarkSharing;
import org.kartishan.bookmarkservice.model.UserBookMark;
import org.kartishan.bookmarkservice.model.dto.BookmarkDTO;
import org.kartishan.bookmarkservice.repository.BookmarkMongoRepository;
import org.kartishan.bookmarkservice.repository.BookmarkSharingRepository;
import org.kartishan.bookmarkservice.repository.UserBookMarkRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookMarkService {
    private final UserBookMarkRepository userBookMarkRepository;
    private final BookmarkMongoRepository bookmarkDocumentRepository;
    private final BookmarkSharingRepository bookmarkSharingRepository;

    public void addOrUpdateBookmark(UUID userId, UUID bookId, BookmarkDTO bookmarkDTO) {
        Optional<UserBookMark> existingUserBookMark = userBookMarkRepository.findByUserIdAndBookId(userId, bookId);

        BookmarkDocument bookmarkDocument;
        if (existingUserBookMark.isPresent()) {
            bookmarkDocument = bookmarkDocumentRepository.findById(existingUserBookMark.get().getBookMarkId())
                    .orElseThrow(() -> new RuntimeException("BookmarkDocument not found"));
        } else {
            bookmarkDocument = new BookmarkDocument();
        }

        BookmarkDocument.Bookmark newBookmark = new BookmarkDocument.Bookmark();
        BeanUtils.copyProperties(bookmarkDTO, newBookmark);
        newBookmark.setId(UUID.randomUUID());
        bookmarkDocument.getBookmarks().add(newBookmark);

        bookmarkDocumentRepository.save(bookmarkDocument);

        if (!existingUserBookMark.isPresent()) {

            UserBookMark userBookMark = new UserBookMark();
            userBookMark.setUserId(userId);
            userBookMark.setBookId(bookId);
            userBookMark.setBookMarkId(bookmarkDocument.getId());

            userBookMarkRepository.save(userBookMark);
        }
    }

    public List<BookmarkDocument.Bookmark> getBookmarks(UUID userId, UUID bookId) {
        UserBookMark userBookMark = userBookMarkRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RuntimeException("UserBookMark not found"));

        String bookmarkDocumentId = userBookMark.getBookMarkId();

        BookmarkDocument bookmarkDocument = bookmarkDocumentRepository.findById(bookmarkDocumentId)
                .orElseThrow(() -> new RuntimeException("BookmarkDocument not found"));

        return bookmarkDocument.getBookmarks();
    }

    public void removeBookmark(UUID userId, UUID bookId, UUID markId) {
        UserBookMark userBookMark = userBookMarkRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RuntimeException("UserBookMark not found"));

        String bookmarkDocumentId = userBookMark.getBookMarkId();

        BookmarkDocument bookmarkDocument = bookmarkDocumentRepository.findById(bookmarkDocumentId)
                .orElseThrow(() -> new RuntimeException("BookmarkDocument not found"));

        boolean removed = bookmarkDocument.getBookmarks().removeIf(bookmark -> markId.equals(bookmark.getId()));
        if (!removed) {
            throw new RuntimeException("Bookmark with specified cfiRange not found");
        }

        bookmarkDocumentRepository.save(bookmarkDocument);
    }

    public UUID shareBookmarks(UUID userId, UUID bookId) {
        UserBookMark userBookMark = userBookMarkRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RuntimeException("UserBookMark not found"));

        BookmarkDocument bookmarkDocument = bookmarkDocumentRepository.findById(userBookMark.getBookMarkId())
                .orElseThrow(() -> new RuntimeException("Bookmark not found"));

        UUID token = UUID.randomUUID();

        BookmarkSharing bookmarkSharing = new BookmarkSharing();
        bookmarkSharing.setId(token);
        bookmarkSharing.setBookMarkId(bookmarkDocument.getId());
        bookmarkSharing.setBookId(bookId);
        bookmarkSharingRepository.save(bookmarkSharing);

        return token;
    }

    public void importBookmarks(UUID recipientUserId, UUID token) {
        BookmarkSharing bookmarkSharing = bookmarkSharingRepository.findById(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        Optional<UserBookMark> optionalRecipientBookMark = userBookMarkRepository.findByUserIdAndBookId(recipientUserId, bookmarkSharing.getBookId());
        BookmarkDocument recipientBookmarkDocument;
        if (optionalRecipientBookMark.isPresent()) {
            recipientBookmarkDocument = bookmarkDocumentRepository.findById(optionalRecipientBookMark.get().getBookMarkId())
                    .orElseThrow(() -> new RuntimeException("BookmarkDocument not found"));
        } else {
            recipientBookmarkDocument = new BookmarkDocument();
            bookmarkDocumentRepository.save(recipientBookmarkDocument);
            UserBookMark newUserBookMark = new UserBookMark();
            newUserBookMark.setUserId(recipientUserId);
            newUserBookMark.setBookId(bookmarkSharing.getBookId());
            newUserBookMark.setBookMarkId(recipientBookmarkDocument.getId());
            userBookMarkRepository.save(newUserBookMark);
        }

        BookmarkDocument sharedBookmarkDocument = bookmarkDocumentRepository.findById(bookmarkSharing.getBookMarkId())
                .orElseThrow(() -> new RuntimeException("Shared BookmarkDocument not found"));
        recipientBookmarkDocument.getBookmarks().addAll(sharedBookmarkDocument.getBookmarks());
        bookmarkDocumentRepository.save(recipientBookmarkDocument);
    }

}
