package com.swacademy.newsletter.web.controller;

import com.swacademy.newsletter.service.news.generation.CardNewsGenerationService;
import com.swacademy.newsletter.web.dto.request.generation.GenerateCardNewsRequestDto;
import com.swacademy.newsletter.web.dto.response.cardnews.GenerateCardNewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card-news")
public class CardNewsController {

    private final CardNewsGenerationService generationService;

    @PostMapping("/generate")
    public GenerateCardNewsResponseDto generateCardNews(
            @RequestBody GenerateCardNewsRequestDto request
    ) {
        GenerateCardNewsResponseDto cardNews = generationService.generateCardNews(request);
        return cardNews;
    }

}
