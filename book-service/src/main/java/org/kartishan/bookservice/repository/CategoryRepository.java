package org.kartishan.bookservice.repository;

import org.kartishan.bookservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByName(String categoryName);

    @Query("SELECT c FROM Category c JOIN c.books b WHERE b.id = :bookId")
    Set<Category> findCategoriesByBookId(@Param("bookId") UUID bookId);

    @Query("SELECT c FROM Category c JOIN c.books b WHERE b.id = :bookId")
    Set<Category> findAllByBooksId(@Param("bookId") UUID bookId);
}
