package com.swacademy.newsletter.service.news;

import com.swacademy.newsletter.apiPayload.code.status.ErrorStatus;
import com.swacademy.newsletter.apiPayload.exception.GeneralException;
import com.swacademy.newsletter.domain.cardnews.CardImage;
import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.repository.news.CardNewsRepository;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardNewsQueryServiceImpl implements CardNewsQueryService {

    private final CardNewsRepository cardNewsRepository;

    @Override
    public CardNewsResponseDto.CardNewsDetailResultDto findCardNewsDetail(Long cardNewsId) {
        CardNews cardNews = cardNewsRepository.findById(cardNewsId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NEWS_NOT_FOUNT));

        List<CardNewsResponseDto.CardNewsItemDto> items = cardNews.getImages().stream()
                .sorted(Comparator.comparing(CardImage::getSeq))
                .map(img -> CardNewsResponseDto.CardNewsItemDto.builder()
                        .newsId(cardNewsId)           // card_image PK
                        .seq(img.getSeq())
                        .imageUrl(img.getImageUrl())
                        .description(img.getDescription())
                        .build()
                )
                .collect(Collectors.toList());

        List<String> tags = cardNews.getCardNewsTags() != null
                ? cardNews.getCardNewsTags().stream().map(t -> t.getTag().getName().getDisplayName()).collect(Collectors.toList())
                : Collections.emptyList();

        return CardNewsResponseDto.CardNewsDetailResultDto.builder()
                .id(cardNews.getId())
                .title(cardNews.getTitle())
                .thumbnailImageUrl(cardNews.getThumbnailImageUrl())
                .newsType(cardNews.getType().getDisplayName())
                .cardNewsItems(items)
                .newsTags(tags)
                .build();
    }
}
