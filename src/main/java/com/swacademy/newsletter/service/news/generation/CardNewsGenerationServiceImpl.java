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
                                String.format("아래 뉴스 문단을 시각적으로 묘사하는 이미지 생성: \"%s\"", paragraph),
                                1
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
