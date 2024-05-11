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
        System.out.println("likedScrollIds: " + likedScrollIds);
        System.out.println("viewedScrollIds: " + viewedScrollIds);
        Map<UUID, Integer> scrollScores = new HashMap<>();

        likedScrollIds.forEach(scrollId -> scrollScores.merge(scrollId, 2, Integer::sum));
        viewedScrollIds.forEach(scrollId -> scrollScores.merge(scrollId, 1, Integer::sum));

        List<UUID> recommendedScrollIds = scrollScores.entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
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
