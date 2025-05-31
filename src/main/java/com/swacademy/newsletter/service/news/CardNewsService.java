package com.swacademy.newsletter.service.news;

import com.swacademy.newsletter.web.dto.request.cardnews.CardNewsRequestDto;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;

public interface CardNewsService {
    CardNewsResponseDto.CardNewsDetailResultDto findCardNewsDetail(Long userId,Long cardNewsId);
    CardNewsResponseDto.ReactionResultDto reactionCardNews(Long userId, CardNewsRequestDto.ReactionRequestDto request);
    CardNewsResponseDto.PracticeResultDto practiceCardNews(Long userId, CardNewsRequestDto.PracticeRequestDto request);
    CardNewsResponseDto.ReadingResultDto readCardNews(Long userId, CardNewsRequestDto.ReadingRequestDto request);
}
