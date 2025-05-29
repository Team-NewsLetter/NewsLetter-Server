package com.swacademy.newsletter.service.news;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;

import java.util.Optional;

// Query는 단순 조회용으로 사용하는 Service
public interface CardNewsQueryService {
    CardNewsResponseDto.CardNewsDetailResultDto findCardNewsDetail(Long cardNewsId);
}
