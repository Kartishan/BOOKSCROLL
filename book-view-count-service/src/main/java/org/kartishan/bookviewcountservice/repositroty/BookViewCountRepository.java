package org.kartishan.bookviewcountservice.repositroty;

import org.kartishan.bookviewcountservice.model.BookViewCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BookViewCountRepository extends JpaRepository<BookViewCount, UUID> {

    @Query("SELECT b FROM BookViewCount b ORDER BY b.viewCount DESC")
    Page<BookViewCount> findTopViewedBooks(Pageable pageable);
}
