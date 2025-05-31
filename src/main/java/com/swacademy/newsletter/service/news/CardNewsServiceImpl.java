package com.swacademy.newsletter.service.news;

import com.swacademy.newsletter.apiPayload.code.status.ErrorStatus;
import com.swacademy.newsletter.apiPayload.exception.GeneralException;
import com.swacademy.newsletter.domain.cardnews.CardImage;
import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsPracticeType;
import com.swacademy.newsletter.domain.enums.CardNewsReactionType;
import com.swacademy.newsletter.domain.enums.CardNewsType;
import com.swacademy.newsletter.domain.mapping.UserNewsHistory;
import com.swacademy.newsletter.domain.mapping.UserNewsReaction;
import com.swacademy.newsletter.domain.mapping.UserPracticeHistory;
import com.swacademy.newsletter.domain.user.Users;
import com.swacademy.newsletter.repository.news.CardNewsRepository;
import com.swacademy.newsletter.repository.user.*;
import com.swacademy.newsletter.web.dto.request.cardnews.CardNewsRequestDto;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardNewsServiceImpl implements CardNewsService {

    private final UserRepository userRepository;
    private final CardNewsRepository cardNewsRepository;
    private final UserNewsReactionRepository userNewsReactionRepository;
    private final UserNewsHistoryRepository userNewsHistoryRepository;
    private final UserPracticeHistoryRepository userPracticeHistoryRepository;

    @Override
    @Transactional
    public CardNewsResponseDto.CardNewsDetailResultDto findCardNewsDetail(Long userId, Long cardNewsId) {
        CardNews cardNews = cardNewsRepository.findById(cardNewsId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NEWS_NOT_FOUNT));

        Optional<UserNewsReaction> userNewsReaction = userNewsReactionRepository.findByUser_IdAndCardNews_Id(userId, cardNewsId);
        Optional<UserPracticeHistory> userPracticeHistory = userPracticeHistoryRepository.findByUser_IdAndCardNews_Id(userId, cardNewsId);

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

        boolean isPracticed = userPracticeHistory.isPresent();
        CardNewsPracticeType practiceType = userPracticeHistory
                .map(UserPracticeHistory::getPracticeStatus)
                .orElse(CardNewsPracticeType.NONE);

        return CardNewsResponseDto.CardNewsDetailResultDto.builder()
                .id(cardNews.getId())
                .title(cardNews.getTitle())
                .thumbnailImageUrl(cardNews.getThumbnailImageUrl())
                .newsType(cardNews.getType().getDisplayName())
                .cardNewsItems(items)
                .newsTags(tags)
                .isReacted(isReacted)
                .reactionType(reactionType)
                .isPracticed(isPracticed)
                .practiceType(practiceType)
                .build();
    }

    @Override
    @Transactional
    public CardNewsResponseDto.ReadingResultDto readCardNews(Long userId, CardNewsRequestDto.ReadingRequestDto request) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        CardNews cardNews = cardNewsRepository.findById(request.getCardNewsId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NEWS_NOT_FOUNT));

        boolean alreadyRead = userNewsHistoryRepository.existsByUserIdAndNewsId(userId, request.getCardNewsId());
        if (!alreadyRead) {
            UserNewsHistory history = UserNewsHistory.builder()
                    .user(user)
                    .newsId(request.getCardNewsId())
                    .newsType(cardNews.getType())
                    .readAt(LocalDateTime.now())
                    .build();
            userNewsHistoryRepository.save(history);
            user.setNewsReadingCount(user.getNewsReadingCount() + 1);
        }

        return CardNewsResponseDto.ReadingResultDto.builder()
                .isRead(true)
                .build();
    }

    @Override
    @Transactional
    public CardNewsResponseDto.ReactionResultDto reactionCardNews(Long userId, CardNewsRequestDto.ReactionRequestDto request) {
        CardNews cardNews = cardNewsRepository.findById(request.getCardNewsId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NEWS_NOT_FOUNT));
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        if (cardNews.getType() == CardNewsType.practice) {
            new GeneralException(ErrorStatus.INVALID_NEWS_TYPE);
        }

        Optional<UserNewsReaction> existing = userNewsReactionRepository.findByUser_IdAndCardNews_Id(userId, request.getCardNewsId());

        if (existing.isEmpty()) {
            userNewsReactionRepository.save(
                    UserNewsReaction.builder()
                            .cardNews(cardNews)
                            .user(users)
                            .reactionType(request.getReaction())
                            .build());
            updateLikeCount(cardNews, request.getReaction(), 1);
        } else {
            UserNewsReaction reaction = existing.get();
            if (reaction.getReactionType() == request.getReaction()) {
                userNewsReactionRepository.delete(reaction);
                updateLikeCount(cardNews, request.getReaction(), -1);
            } else {
                updateLikeCount(cardNews, reaction.getReactionType(), -1); // 기존 반응 감소
                updateLikeCount(cardNews, request.getReaction(), 1);      // 새로운 반응 증가
                reaction.changeReaction(request.getReaction());
            }
        }

        return CardNewsResponseDto.ReactionResultDto.builder()
                .reaction(request.getReaction())
                .likeCount(cardNews.getLikes())
                .dislikeCount(cardNews.getDislikes())
                .build();
    }

    @Override
    @Transactional
    public CardNewsResponseDto.PracticeResultDto practiceCardNews(Long userId, CardNewsRequestDto.PracticeRequestDto request) {
        CardNews cardNews = cardNewsRepository.findById(request.getCardNewsId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.CARD_NEWS_NOT_FOUNT));

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        if (cardNews.getType() != CardNewsType.practice) {
            throw new GeneralException(ErrorStatus.INVALID_NEWS_TYPE);
        }

        Optional<UserPracticeHistory> existing = userPracticeHistoryRepository.findByUser_IdAndCardNews_Id(userId, request.getCardNewsId());

        if (existing.isEmpty()) {
            // 신규 기록 저장
            userPracticeHistoryRepository.save(
                    UserPracticeHistory.builder()
                            .user(user)
                            .cardNews(cardNews)
                            .practiceStatus(request.getPracticeType())
                            .build()
            );

            if (request.getPracticeType() == CardNewsPracticeType.PRACTICE) {
                user.setPracticeCount(user.getPracticeCount() + 1); // 실천이면 카운트 증가
            }
        } else {
            UserPracticeHistory history = existing.get();

            if (history.getPracticeStatus() == request.getPracticeType()) {
                userPracticeHistoryRepository.delete(history);
                if (request.getPracticeType() == CardNewsPracticeType.PRACTICE) {
                    user.setPracticeCount(user.getPracticeCount() - 1); // PRACTICE 취소 → 감소
                }
            } else {
                if (history.getPracticeStatus() == CardNewsPracticeType.PRACTICE) {
                    user.setPracticeCount(user.getPracticeCount() - 1);
                } else if (request.getPracticeType() == CardNewsPracticeType.PRACTICE) {
                    user.setPracticeCount(user.getPracticeCount() + 1);
                }
                history.setPracticeStatus(request.getPracticeType());
            }
        }

        return CardNewsResponseDto.PracticeResultDto.builder()
                .isPracticed(request.getPracticeType())
                .build();
    }

    private void updateLikeCount(CardNews cardNews, CardNewsReactionType type, int delta) {
        if (type == CardNewsReactionType.LIKE) {
            cardNews.setLikes(cardNews.getLikes() + delta);
        } else {
            cardNews.setDislikes(cardNews.getDislikes() + delta);
        }
    }
}
