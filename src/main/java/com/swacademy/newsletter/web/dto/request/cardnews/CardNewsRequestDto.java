package com.swacademy.newsletter.web.dto.request.cardnews;

import com.swacademy.newsletter.domain.enums.CardNewsReactionType;
import lombok.*;

public class CardNewsRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CardNewsReactionRequestDto {
        private Long cardNewsId;
        private CardNewsReactionType reaction; // LIKE or DISLIKE
    }
}
