package org.kartishan.scrolluserlikeservice.reposirory;

import org.kartishan.scrolluserlikeservice.model.UserScrollLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserScrollLikeRepository extends JpaRepository<UserScrollLike, UUID> {

    Optional<UserScrollLike> findByUserIdAndScrollId(UUID userId, UUID scrollId);

    List<UserScrollLike> findAllByUserIdAndLikedIsTrue(UUID userId);

    @Query("SELECT DISTINCT u.userId FROM UserScrollLike u")
    List<UUID> findAllUsers();
}