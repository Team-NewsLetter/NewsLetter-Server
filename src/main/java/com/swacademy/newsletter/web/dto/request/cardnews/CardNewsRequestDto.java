package com.swacademy.newsletter.web.dto.request.cardnews;

import com.swacademy.newsletter.domain.enums.CardNewsPracticeType;
import com.swacademy.newsletter.domain.enums.CardNewsReactionType;
import lombok.*;

public class CardNewsRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReactionRequestDto {
        private Long cardNewsId;
        private CardNewsReactionType reaction; // LIKE or DISLIKE
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PracticeRequestDto {
        private Long cardNewsId;
        private CardNewsPracticeType practiceType; // PRACTICE or NOT_PRACTICE
    }

}
