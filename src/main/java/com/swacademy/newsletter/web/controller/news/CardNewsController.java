package com.swacademy.newsletter.web.controller.news;

import com.swacademy.newsletter.apiPayload.ApiResponse;
import com.swacademy.newsletter.converter.CardNewsConverter;
import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsTagType;
import com.swacademy.newsletter.domain.enums.CardNewsType;
import com.swacademy.newsletter.service.news.CardNewsService;
import com.swacademy.newsletter.service.news.generation.CardNewsGenerationService;
import com.swacademy.newsletter.service.news.list.CardNewsListService;
import com.swacademy.newsletter.web.dto.request.cardnews.CardNewsRequestDto;
import com.swacademy.newsletter.web.dto.request.generation.GenerateCardNewsRequestDto;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card-news")
public class CardNewsController {

    private final CardNewsService cardNewsService;
    private final CardNewsGenerationService generationService;
    private final CardNewsListService cardNewsListService;

    @GetMapping("/{cardNewsId}/detail")
    @Operation(summary = "카드뉴스 상세보기 API", description = "특정 카드뉴스 상세보기 API로 이미지와 내용을 담고 있습니다.")
    public ApiResponse<CardNewsResponseDto.CardNewsDetailResultDto> getCardNewsDetail(
            @AuthenticationPrincipal Long userId,
            @PathVariable("cardNewsId") Long cardNewsId
    ) {
        return ApiResponse.onSuccess(
                cardNewsService.findCardNewsDetail(userId, cardNewsId)
        );
    }

    @GetMapping
    @Operation(summary = "카드뉴스 리스트 API", description = "카드뉴스 리스트 API로 parameter type에 따른 리스트를 제공합니다.")
    public ApiResponse<CardNewsResponseDto.CardNewsListResultDto> getCardNewsList(
            @AuthenticationPrincipal Long userId,
            @RequestParam("type") CardNewsType type,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "tag", required = false) CardNewsTagType tag
    ) {
        Slice<CardNews> cardNewsList;
        if (type == CardNewsType.daily && tag != null) {
            cardNewsList = cardNewsListService.getDailyCardNewsByTag(tag, page, size);
        } else {
            cardNewsList = cardNewsListService.getCardNewsList(type, page, size);
        }
        return ApiResponse.onSuccess(CardNewsConverter.toListResponseDto(cardNewsList));
    }

    @PostMapping("/generate")
    @Operation(summary = "카드뉴스 생성 API", description = "카드뉴스 생성 API로 Python 스크립트에서 요청할 수 있습니다.")
    public CardNewsResponseDto.GenerateCardNewsResultDto generateCardNews(
            @RequestBody GenerateCardNewsRequestDto request
    ) {
        return generationService.generateCardNews(request);
    }


}
