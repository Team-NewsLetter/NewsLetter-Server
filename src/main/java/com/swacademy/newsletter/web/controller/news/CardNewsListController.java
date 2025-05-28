package com.swacademy.newsletter.web.controller.news;

import com.swacademy.newsletter.apiPayload.ApiResponse;
import com.swacademy.newsletter.converter.CardNewsConverter;
import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsType;
import com.swacademy.newsletter.service.news.list.CardNewsListService;
import com.swacademy.newsletter.web.dto.response.list.CardNewsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card-news")
public class CardNewsListController {

    private final CardNewsListService cardNewsListService;

    @GetMapping
    public ApiResponse<CardNewsListResponseDto> getCardNewsList(
            @RequestParam("type") CardNewsType type,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {

        Slice<CardNews> cardNewsList = cardNewsListService.getCardNewsList(
                type,
                page,
                size
        );
        return ApiResponse.onSuccess(CardNewsConverter.toListResponseDto(cardNewsList));
    }
}
