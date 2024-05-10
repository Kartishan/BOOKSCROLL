package org.kartishan.scrolluserlikeservice.service;

import lombok.AllArgsConstructor;
import org.kartishan.scrolluserlikeservice.model.UserScrollLike;
import org.kartishan.scrolluserlikeservice.reposirory.UserScrollLikeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        return userScrollLikeRepository.findByUserIdAndScrollId(userId, scrollId).get().isLiked();
    }
}
