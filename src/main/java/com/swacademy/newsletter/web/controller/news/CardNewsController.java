package com.swacademy.newsletter.web.controller.news;

import com.swacademy.newsletter.apiPayload.ApiResponse;
import com.swacademy.newsletter.service.news.CardNewsQueryService;
import com.swacademy.newsletter.service.news.generation.CardNewsGenerationService;
import com.swacademy.newsletter.web.dto.request.generation.GenerateCardNewsRequestDto;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card-news")
public class CardNewsController {

    private final CardNewsQueryService cardNewsQueryService;
    private final CardNewsGenerationService generationService;


    @GetMapping("/{cardNewsId}/detail")
    public ApiResponse<CardNewsResponseDto.CardNewsDetailResultDto> getCardNewsDetail(
            @PathVariable("cardNewsId") Long cardNewsId
    ) {
        return ApiResponse.onSuccess(
                cardNewsQueryService.findCardNewsDetail(cardNewsId)
        );
    }

    @PostMapping("/generate")
    public CardNewsResponseDto.GenerateCardNewsResultDto generateCardNews(
            @RequestBody GenerateCardNewsRequestDto request
    ) {
        return generationService.generateCardNews(request);
    }
}
