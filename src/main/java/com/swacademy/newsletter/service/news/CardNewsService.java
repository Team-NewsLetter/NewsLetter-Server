package com.swacademy.newsletter.service.news;

import com.swacademy.newsletter.web.dto.request.cardnews.CardNewsRequestDto;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;

// Query는 단순 조회용으로 사용하는 Service
public interface CardNewsService {
    CardNewsResponseDto.CardNewsDetailResultDto findCardNewsDetail(Long userId,Long cardNewsId);
    CardNewsResponseDto.CardNewsReactionResultDto reactionCardNews(Long userId, CardNewsRequestDto.CardNewsReactionRequestDto request);
}
