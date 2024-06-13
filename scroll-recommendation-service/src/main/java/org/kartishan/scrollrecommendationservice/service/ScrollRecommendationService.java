package org.kartishan.scrollrecommendationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kartishan.scrollrecommendationservice.feign.ScrollClient;
import org.kartishan.scrollrecommendationservice.feign.UserScrollLikeClient;
import org.kartishan.scrollrecommendationservice.feign.UserScrollViewHistoryClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScrollRecommendationService {
    private final UserScrollLikeClient userScrollLikeClient;
    private final UserScrollViewHistoryClient userScrollViewHistoryClient;
    private final ScrollClient scrollClient;

    public List<UUID> getPersonalizedScrolls(UUID userId, int limit) {
        List<UUID> likedScrollIds = userScrollLikeClient.getLikedScrollsByUser(userId);
        List<UUID> viewedScrollIds = userScrollViewHistoryClient.getLikedScrollsByUser(userId);

        Set<UUID> allUserIds = new HashSet<>(userScrollLikeClient.getAllUsers());
        allUserIds.addAll(userScrollViewHistoryClient.getAllUsers());

        Map<UUID, Double> userSimilarities = new HashMap<>();
        for (UUID otherUserId : allUserIds) {
            if (!otherUserId.equals(userId)) {
                List<UUID> otherLikedScrollIds = userScrollLikeClient.getLikedScrollsByUser(otherUserId);
                List<UUID> otherViewedScrollIds = userScrollViewHistoryClient.getLikedScrollsByUser(otherUserId);

                Set<UUID> commonLikedScrolls = new HashSet<>(likedScrollIds);
                commonLikedScrolls.retainAll(otherLikedScrollIds);

                Set<UUID> commonViewedScrolls = new HashSet<>(viewedScrollIds);
                commonViewedScrolls.retainAll(otherViewedScrollIds);

                double similarity = (2.0 * commonLikedScrolls.size() + commonViewedScrolls.size()) /
                        (Math.sqrt(likedScrollIds.size() * otherLikedScrollIds.size()) + Math.sqrt(viewedScrollIds.size() * otherViewedScrollIds.size()));

                userSimilarities.put(otherUserId, similarity);
            }
        }

        Map<UUID, Double> recommendedScrollScores = new HashMap<>();
        userSimilarities.entrySet().stream()
                .sorted(Map.Entry.<UUID, Double>comparingByValue().reversed())
                .limit(limit)
                .forEach(entry -> {
                    UUID similarUserId = entry.getKey();
                    double similarity = entry.getValue();

                    List<UUID> similarUserLikedScrollIds = userScrollLikeClient.getLikedScrollsByUser(similarUserId);
                    List<UUID> similarUserViewedScrollIds = userScrollViewHistoryClient.getLikedScrollsByUser(similarUserId);

                    for (UUID scrollId : similarUserLikedScrollIds) {
                        if (!likedScrollIds.contains(scrollId) && !viewedScrollIds.contains(scrollId)) {
                            recommendedScrollScores.merge(scrollId, similarity * 2, Double::sum);
                        }
                    }

                    for (UUID scrollId : similarUserViewedScrollIds) {
                        if (!likedScrollIds.contains(scrollId) && !viewedScrollIds.contains(scrollId)) {
                            recommendedScrollScores.merge(scrollId, similarity, Double::sum);
                        }
                    }
                });

        List<UUID> recommendedScrollIds = recommendedScrollScores.entrySet().stream()
                .sorted(Map.Entry.<UUID, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(limit)
                .collect(Collectors.toList());

        if (recommendedScrollIds.size() < limit) {
            int neededCount = limit - recommendedScrollIds.size();
            List<UUID> randomScrollIds = scrollClient.getRandomScrollIds(neededCount);
            randomScrollIds.removeAll(recommendedScrollIds);
            recommendedScrollIds.addAll(randomScrollIds);
        }

        return recommendedScrollIds;
    }
}
