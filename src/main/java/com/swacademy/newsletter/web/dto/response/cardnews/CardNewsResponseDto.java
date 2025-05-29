package com.swacademy.newsletter.web.dto.response.cardnews;

import com.swacademy.newsletter.domain.enums.CardNewsType;
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
        private CardNewsType newsType;
        // card_image 단일 값을 모아서 주는 response Dto
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardNewsItemDto {
        private Long newsId;
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
        private String newsTag;
        private LocalDateTime createdAt;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class GenerateCardNewsResultDto {
        private String title;
        private List<String> summary;
        private List<String> imageUrl; //0번째가 thumbnail
    }
}
