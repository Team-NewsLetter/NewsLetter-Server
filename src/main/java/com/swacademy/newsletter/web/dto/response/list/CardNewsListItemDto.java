package com.swacademy.newsletter.web.dto.response.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardNewsListItemDto {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String newsTag;
    private LocalDateTime createdAt;
}
