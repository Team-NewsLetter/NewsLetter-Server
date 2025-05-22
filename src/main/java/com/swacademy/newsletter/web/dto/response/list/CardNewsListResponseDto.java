package com.swacademy.newsletter.web.dto.response.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardNewsListResponseDto {
    private Slice<CardNewsListItemDto> cardNews;
}
