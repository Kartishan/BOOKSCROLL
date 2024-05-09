package org.kartishan.bookservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kartishan.bookservice.exceptions.BookNotFoundException;
import org.kartishan.bookservice.model.Book;
import org.kartishan.bookservice.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookRatingService {

    private final BookRepository bookRepository;


    public void updateBookRating(UUID bookId, int rating) {
        try {
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book with id: " + bookId + " not found"));
            book.setRating(rating);
            bookRepository.save(book);
        }catch (Exception e){

        }
    }
}
