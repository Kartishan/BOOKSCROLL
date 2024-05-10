package org.kartishan.bookviewcountservice.repositroty;

import org.kartishan.bookviewcountservice.model.BookViewCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookViewCountRepository extends JpaRepository<BookViewCount, UUID> {
    
}
