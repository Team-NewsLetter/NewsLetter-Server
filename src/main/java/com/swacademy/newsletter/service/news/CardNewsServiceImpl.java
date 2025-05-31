package com.swacademy.newsletter.service.news;

import com.swacademy.newsletter.apiPayload.code.status.ErrorStatus;
import com.swacademy.newsletter.apiPayload.exception.GeneralException;
import com.swacademy.newsletter.domain.cardnews.CardImage;
import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsReactionType;
import com.swacademy.newsletter.domain.mapping.UserNewsReaction;
import com.swacademy.newsletter.domain.user.Users;
import com.swacademy.newsletter.repository.news.CardNewsRepository;
import com.swacademy.newsletter.repository.user.UserNewsReactionRepository;
import com.swacademy.newsletter.repository.user.UserRepository;
import com.swacademy.newsletter.web.dto.request.cardnews.CardNewsRequestDto;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardNewsServiceImpl implements CardNewsService {

    private final CardNewsRepository cardNewsRepository;
    private final UserNewsReactionRepository userNewsReactionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CardNewsResponseDto.CardNewsDetailResultDto findCardNewsDetail(Long userId, Long cardNewsId) {
        CardNews cardNews = cardNewsRepository.findById(cardNewsId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NEWS_NOT_FOUNT));

        Optional<UserNewsReaction> userNewsReaction = userNewsReactionRepository.findByUser_IdAndCardNews_Id(userId, cardNewsId);

        List<CardNewsResponseDto.CardNewsItemDto> items = cardNews.getImages().stream()
                .sorted(Comparator.comparing(CardImage::getSeq))
                .map(img -> CardNewsResponseDto.CardNewsItemDto.builder()
                        .seq(img.getSeq())
                        .imageUrl(img.getImageUrl())
                        .description(img.getDescription())
                        .build()
                )
                .collect(Collectors.toList());

        List<String> tags = cardNews.getCardNewsTags() != null
                ? cardNews.getCardNewsTags().stream().map(t -> t.getTag().getName().getDisplayName()).collect(Collectors.toList())
                : Collections.emptyList();

        boolean isReacted = userNewsReaction.isPresent();
        CardNewsReactionType reactionType = userNewsReaction
                .map(UserNewsReaction::getReactionType)
                .orElse(CardNewsReactionType.NONE);

        return CardNewsResponseDto.CardNewsDetailResultDto.builder()
                .id(cardNews.getId())
                .title(cardNews.getTitle())
                .thumbnailImageUrl(cardNews.getThumbnailImageUrl())
                .newsType(cardNews.getType().getDisplayName())
                .cardNewsItems(items)
                .newsTags(tags)
                .isReacted(isReacted)
                .reactionType(reactionType)
                .build();
    }

    @Override
    @Transactional
    public CardNewsResponseDto.CardNewsReactionResultDto reactionCardNews(Long userId, CardNewsRequestDto.CardNewsReactionRequestDto request) {
        CardNews cardNews = cardNewsRepository.findById(request.getCardNewsId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NEWS_NOT_FOUNT));
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Optional<UserNewsReaction> existing = userNewsReactionRepository.findByUser_IdAndCardNews_Id(userId, request.getCardNewsId());

        if (existing.isEmpty()) {
            userNewsReactionRepository.save(
                    UserNewsReaction.builder()
                            .cardNews(cardNews)
                            .user(users)
                            .reactionType(request.getReaction())
                            .build());
            updateCount(cardNews, request.getReaction(), 1);
        } else {
            UserNewsReaction reaction = existing.get();
            if (reaction.getReactionType() == request.getReaction()) {
                userNewsReactionRepository.delete(reaction);
                updateCount(cardNews, request.getReaction(), -1);
            } else {
                updateCount(cardNews, reaction.getReactionType(), -1); // 기존 반응 감소
                updateCount(cardNews, request.getReaction(), 1);      // 새로운 반응 증가
                reaction.changeReaction(request.getReaction());
            }
        }

        return CardNewsResponseDto.CardNewsReactionResultDto.builder()
                .reaction(request.getReaction())
                .likeCount(cardNews.getLikes())
                .dislikeCount(cardNews.getDislikes())
                .build();
    }

    private void updateCount(CardNews cardNews, CardNewsReactionType type, int delta) {
        if (type == CardNewsReactionType.LIKE) {
            cardNews.setLikes(cardNews.getLikes() + delta);
        } else {
            cardNews.setDislikes(cardNews.getDislikes() + delta);
        }
    }
}
