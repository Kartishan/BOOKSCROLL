package org.kartishan.bookheroservice.repository;

import org.kartishan.bookheroservice.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface HeroRepository extends JpaRepository<Hero, UUID> {
    List<Hero> findByBookId(UUID bookId);

    @Query("SELECT h FROM Hero h WHERE :parent MEMBER OF h.parents")
    Set<Hero> findChildrenByHeroId(@Param("parent") Hero parent);
}
