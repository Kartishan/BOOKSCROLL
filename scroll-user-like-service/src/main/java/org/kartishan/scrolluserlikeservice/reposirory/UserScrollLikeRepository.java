package org.kartishan.scrolluserlikeservice.reposirory;

import org.kartishan.scrolluserlikeservice.model.UserScrollLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserScrollLikeRepository extends JpaRepository<UserScrollLike, UUID> {
    Optional<UserScrollLike> findByScrollId(UUID id);

    List<UserScrollLike> findAllByScrollId(UUID scrollId);

    List<UserScrollLike> findAllByUserId(UUID userId);

    Optional<UserScrollLike> findByUserIdAndScrollId(UUID userId, UUID scrollId);

    List<UserScrollLike> findAllByUserIdAndLikedIsTrue(UUID userId);
}