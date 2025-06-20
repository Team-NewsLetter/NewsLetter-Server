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
                                        "Generate an illustration that visually represents the news paragraph below. " +
                                                "The image must not contain any text, letters, or numbers. " +
                                                "Use a soft, consistent cartoon-style with clean lines and warm, harmonious colors. " +
                                                "The illustration should convey emotion and be suitable for a card-news style layout. " +
                                                "Avoid harsh contrast, photo-realism, or detailed textures — keep the style clean, minimal, and unified. " +
                                                "News paragraph: \"%s\"", paragraph
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
