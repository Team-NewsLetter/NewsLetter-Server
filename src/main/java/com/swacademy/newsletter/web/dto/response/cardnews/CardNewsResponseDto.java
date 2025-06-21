package com.swacademy.newsletter.web.dto.response.cardnews;

import com.swacademy.newsletter.domain.enums.CardNewsPracticeType;
import com.swacademy.newsletter.domain.enums.CardNewsReactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

public class CardNewsResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardNewsDetailResultDto {
        private Long id;
        private String title;
        private String thumbnailImageUrl;
        private List<CardNewsItemDto> cardNewsItems;
        private List<String> newsTags;
        private String newsType;
        private Boolean isRead;
        private Boolean isReacted; // 유저 추천 여부 (o/x)
        private CardNewsReactionType reactionType; // 유저 추천 타입 (추천, 비추천, none)
        private Boolean isPracticed; // 유저 실천 여부 (o/x)
        private CardNewsPracticeType practiceType; // 유저 실천 타입 (실천, 비실천, none)
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardNewsItemDto {
        private Integer seq;
        private String imageUrl;
        private String description;
        // db card_image에 매핑
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardNewsListResultDto {
        private Slice<CardNewsListItemDto> cardNews;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardNewsListItemDto {
        private Long id;
        private String title;
        private String thumbnailUrl;
        private String newsType;
        private LocalDateTime createdAt;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GenerateCardNewsResultDto {
        private String title;
        private List<String> summary;
        private List<String> imageUrl; //0번째가 thumbnail
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReactionResultDto {
        private CardNewsReactionType reaction; // LIKE, DISLIKE
        private int likeCount;
        private int dislikeCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PracticeResultDto {
        private CardNewsPracticeType isPracticed;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadingResultDto {
        private boolean isRead;
    }
}
