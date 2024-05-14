package org.kartishan.bookservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kartishan.bookservice.exceptions.BookNotFoundException;
import org.kartishan.bookservice.model.Book;
import org.kartishan.bookservice.model.Category;
import org.kartishan.bookservice.model.dto.BookDTO;
import org.kartishan.bookservice.model.dto.CategoryDTO;
import org.kartishan.bookservice.model.dto.ViewHistoryMessage;
import org.kartishan.bookservice.rabbitMQ.RabbitMQProducer;
import org.kartishan.bookservice.repository.BookRepository;
import org.kartishan.bookservice.repository.CategoryRepository;
import org.kartishan.bookservice.request.BookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final RabbitMQProducer rabbitMQProducer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Book getBookById(UUID id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
    }

    public BookDTO getBookByIdWithCategory(UUID id, UUID userId) {
        Book book = getBookById(id);
        if (userId != null){
            processBookView(userId, book.getId());
        }
        return convertToDto(book);
    }

    public void processBookView(UUID userId, UUID bookId) {
        ViewHistoryMessage message = new ViewHistoryMessage(userId, bookId, new Date());

        try {
            String messageAsString = objectMapper.writeValueAsString(message);
            rabbitMQProducer.sendToQueue("myQueue", messageAsString);
            String bookIdAsString = bookId.toString();
            rabbitMQProducer.sendToQueue("bookViewIncrementQueue", bookIdAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public BookDTO convertToDto(Book book) {
        Set<Category> categories = categoryRepository.findCategoriesByBookId(book.getId());

        Set<String> categoryNames = categories.stream().map(Category::getName).collect(Collectors.toSet());

        BookDTO bookDTO = BookDTO.builder().id(book.getId()).name(book.getName()).author(book.getAuthor()).authorFull(book.getAuthorFull()).description(book.getDescription()).rating(book.getRating()).pageCount(book.getPageCount()).categories(categoryNames).build();
        return bookDTO;
    }

    public Page<BookDTO> getAllBooks(Integer page, Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<Book> bookPage = bookRepository.findAll(pageable);
            Page<BookDTO> dtoPage = bookPage.map(this::convertToDto);
            return dtoPage;
        } catch (Exception e) {
            throw new BookNotFoundException("Books not found! More: " + e);
        }
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    public Page<Book> getBooksByPartialName(String partialName, Integer page, Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize);
            return bookRepository.findByNameContaining(partialName, pageable);
        } catch (Exception e) {
            throw new BookNotFoundException("Books by partial name " + partialName + " not found");
        }
    }

    public Page<BookDTO> getBooksByCategory(String category, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        try {
            Page<Book> booksPage = bookRepository.findByCategory(category, pageable);
            return booksPage.map(this::convertToDto);
        } catch (Exception e) {
            throw new BookNotFoundException("Books by category " + category + " not found");
        }
    }

    public Page<BookDTO> getTopRatedBooks(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "rating"));
        Page<Book> booksPage = bookRepository.findAll(pageable);
        return booksPage.map(this::convertToDto);
    }

    public void createBook(BookRequest bookRequest) {
        System.out.println(bookRequest);
        try {
            Book book = new Book();
            book.setName(bookRequest.getName());
            book.setAuthor(bookRequest.getAuthor());
            book.setAuthorFull(bookRequest.getAuthorFullName());
            book.setDescription(bookRequest.getDescription());
            book.setPageCount(bookRequest.getPageCount());
            book.setRating(0);

            Set<Category> categories = new HashSet<>();
            for (String categoryName : bookRequest.getCategories()) {
                Category category = categoryRepository.findByName(categoryName).orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(categoryName);
                    return categoryRepository.save(newCategory);
                });
                categories.add(category);
            }
            book.setCategories(categories);
            bookRepository.save(book);
        } catch (Exception e) {
            log.error("Error creating book: " + e.getMessage(), e);
        }
    }

    public void changeBookInformation(Book book) {
        try {
            UUID id = book.getId();
            Optional<Book> newBook = bookRepository.findById(id);
            newBook = Optional.of(book);
            bookRepository.save(newBook.get());
        } catch (Exception e) {
            log.error("Error changing book: " + e);
        }

    }

    public List<BookDTO> getBooksByIds(List<UUID> bookIds) {
        List<Book> books = bookRepository.findAllById(bookIds);
        return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Set<CategoryDTO> findCategoriesByBookId(UUID bookId) {
        return categoryRepository.findCategoriesByBookId(bookId).stream()
                .map(category -> CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toSet());
    }

    public List<String> getAllCategoryNames() {
        List<Category> categories = categoryRepository.findAll();
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.getName());
        }
        return categoryNames;
    }
}
