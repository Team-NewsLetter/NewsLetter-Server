package com.swacademy.newsletter.web.controller.news;

import com.swacademy.newsletter.apiPayload.ApiResponse;
import com.swacademy.newsletter.service.news.CardNewsService;
import com.swacademy.newsletter.service.news.generation.CardNewsGenerationService;
import com.swacademy.newsletter.service.news.list.CardNewsListService;
import com.swacademy.newsletter.web.dto.request.cardnews.CardNewsRequestDto;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card-news")
public class CardNewsReactionController {

    private final CardNewsService cardNewsService;

    @PostMapping("/reaction")
    @Operation(summary = "카드뉴스 추천/비추천 API", description = "카드뉴스 추천/비추천 API로 리액션 타입(LIKE, DISLIKE)으로 요청할 수 있습니다.")
    public ApiResponse<CardNewsResponseDto.ReactionResultDto> postUserReactionCardNews(
            @AuthenticationPrincipal Long userId,
            @RequestBody CardNewsRequestDto.ReactionRequestDto request
    ) {
        return ApiResponse.onSuccess(cardNewsService.reactionCardNews(userId, request));
    }

    @PostMapping("/practice")
    @Operation(summary = "카드뉴스 실천/비실천 API", description = "카드뉴스 실천/비실천 API로 리액션 타입(PRACTICE, NOT_PRACTICE)으로 요청할 수 있습니다.")
    public ApiResponse<CardNewsResponseDto.PracticeResultDto> postUserReactionCardNews(
            @AuthenticationPrincipal Long userId,
            @RequestBody CardNewsRequestDto.PracticeRequestDto request
    ) {
        return ApiResponse.onSuccess(cardNewsService.practiceCardNews(userId, request));
    }
}
