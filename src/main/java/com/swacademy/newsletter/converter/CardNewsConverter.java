package com.swacademy.newsletter.converter;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.tag.CardNewsTags;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;
import org.springframework.data.domain.Slice;

public class CardNewsConverter {

    public static CardNewsResponseDto.CardNewsListItemDto toListItemDto(CardNews cardNews) {
        // 첫 번째 태그 링크 추출
        CardNewsTags firstTagLink = cardNews.getCardNewsTags().isEmpty()
                ? null
                : cardNews.getCardNewsTags().get(0);
        String tagName = null;
        if (firstTagLink != null && firstTagLink.getTag() != null) {
            tagName = firstTagLink.getTag().getName().getDisplayName();
        }

        return CardNewsResponseDto.CardNewsListItemDto.builder()
                .id(cardNews.getId())
                .title(cardNews.getTitle())
                .thumbnailUrl(cardNews.getThumbnailImageUrl())
                .newsTag(tagName)
                .newsType(cardNews.getType().getDisplayName())
                .createdAt(cardNews.getCreatedAt())
                .build();
    }

    public static CardNewsResponseDto.CardNewsListResultDto toListResponseDto(
            Slice<CardNews> newsSlice
    ) {
        // Slice.map으로 DTO 변환
        Slice<CardNewsResponseDto.CardNewsListItemDto> dtoSlice = newsSlice.map(CardNewsConverter::toListItemDto);

        return CardNewsResponseDto.CardNewsListResultDto.builder()
                .cardNews(dtoSlice)
                .build();
    }
}