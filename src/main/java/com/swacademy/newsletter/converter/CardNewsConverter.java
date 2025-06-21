package com.swacademy.newsletter.converter;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.tag.CardNewsTags;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;
import org.springframework.data.domain.Slice;

public class CardNewsConverter {

    public static CardNewsResponseDto.CardNewsListItemDto toListItemDto(CardNews cardNews) {
        return CardNewsResponseDto.CardNewsListItemDto.builder()
                .id(cardNews.getId())
                .title(cardNews.getTitle())
                .thumbnailUrl(cardNews.getThumbnailImageUrl())
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