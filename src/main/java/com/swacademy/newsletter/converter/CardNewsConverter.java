package com.swacademy.newsletter.converter;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.tag.CardNewsTags;
import com.swacademy.newsletter.domain.tag.NewsTag;
import com.swacademy.newsletter.web.dto.response.list.CardNewsListItemDto;
import com.swacademy.newsletter.web.dto.response.list.CardNewsListResponseDto;
import org.springframework.data.domain.Slice;

import java.util.List;


public class CardNewsConverter {

    public static CardNewsListItemDto toListItemDto(CardNews cardNews) {
        // 첫 번째 태그 링크 추출
        CardNewsTags firstTagLink = cardNews.getCardNewsTags().isEmpty()
                ? null
                : cardNews.getCardNewsTags().get(0);
        String tagName = null;
        if (firstTagLink != null && firstTagLink.getTag() != null) {
            tagName = firstTagLink.getTag().getName().name();
        }

        return CardNewsListItemDto.builder()
                .id(cardNews.getId())
                .title(cardNews.getTitle())
                .thumbnailUrl(cardNews.getThumbnailImageUrl())
                .newsTag(tagName)
                .createdAt(cardNews.getCreatedAt())
                .build();
    }

    public static CardNewsListResponseDto toListResponseDto(
            Slice<CardNews> newsSlice
    ) {
        // Slice.map으로 DTO 변환
        Slice<CardNewsListItemDto> dtoSlice = newsSlice.map(CardNewsConverter::toListItemDto);

        return CardNewsListResponseDto.builder()
                .cardNews(dtoSlice)
                .build();
    }
}