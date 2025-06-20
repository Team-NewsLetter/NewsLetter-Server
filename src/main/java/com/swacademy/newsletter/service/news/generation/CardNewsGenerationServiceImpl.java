package com.swacademy.newsletter.service.news.generation;

import com.swacademy.newsletter.web.dto.request.generation.GenerateCardNewsRequestDto;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardNewsGenerationServiceImpl implements CardNewsGenerationService {

    private final NewsSummaryService newsSummaryService;
    private final ImageGenerationService imageGenerationService;

    @Override
    public CardNewsResponseDto.GenerateCardNewsResultDto generateCardNews(GenerateCardNewsRequestDto request) {
        List<String> paragraphs = newsSummaryService.summarizeAndSplit(request.getText(), request.getTitle());

        List<String> imageUrls = paragraphs.stream()
                .flatMap(paragraph ->
                        imageGenerationService.generateImages(
                                String.format(
                                        "Illustrate the following news paragraph as part of a cohesive, minimal, and emotional cartoon-style series. Keep the style unified: no text, no numbers, soft colors, clean lines. Paragraph: \"%s\"",
                                        paragraph
                                ), 1
                        ).stream()
                )
                .collect(Collectors.toList());

        return CardNewsResponseDto.GenerateCardNewsResultDto.builder()
                .title(request.getTitle())
                .summary(paragraphs)
                .imageUrl(imageUrls)
                .build();
    }
}
