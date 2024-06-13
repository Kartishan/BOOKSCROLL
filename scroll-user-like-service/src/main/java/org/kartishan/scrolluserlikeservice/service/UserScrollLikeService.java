package org.kartishan.scrolluserlikeservice.service;

import lombok.AllArgsConstructor;
import org.kartishan.scrolluserlikeservice.model.UserScrollLike;
import org.kartishan.scrolluserlikeservice.reposirory.UserScrollLikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserScrollLikeService {
    private final UserScrollLikeRepository userScrollLikeRepository;

    public void likeScroll(UUID userId, UUID scrollId) {
        UserScrollLike userScrollLike = userScrollLikeRepository.findByUserIdAndScrollId(userId, scrollId)
                .orElseGet(() -> {
                    UserScrollLike newUserScrollLike = new UserScrollLike();
                    newUserScrollLike.setUserId(userId);
                    newUserScrollLike.setScrollId(scrollId);
                    newUserScrollLike.setLiked(false);
                    return newUserScrollLike;
                });
        userScrollLike.setLiked(!userScrollLike.isLiked());
        userScrollLikeRepository.save(userScrollLike);
    }

    public boolean userLike(UUID scrollId, UUID userId){
        Optional<UserScrollLike> like = userScrollLikeRepository.findByUserIdAndScrollId(userId, scrollId);
        System.out.println("like: " + like.get().isLiked());
        return like.map(UserScrollLike::isLiked).orElse(false);
    }


    public List<UUID> getLikedScrollIdsByUser(UUID userId) {
        return userScrollLikeRepository.findAllByUserIdAndLikedIsTrue(userId).stream()
                .map(UserScrollLike::getScrollId)
                .collect(Collectors.toList());
    }
    public List<UUID> getAllUsers() {
        return userScrollLikeRepository.findAllUsers();
    }
}
