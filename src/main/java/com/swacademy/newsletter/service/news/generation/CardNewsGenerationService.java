package com.swacademy.newsletter.service.news.generation;

import com.swacademy.newsletter.web.dto.request.generation.GenerateCardNewsRequestDto;
import com.swacademy.newsletter.web.dto.response.cardnews.CardNewsResponseDto;

public interface CardNewsGenerationService {

   CardNewsResponseDto.GenerateCardNewsResultDto generateCardNews(GenerateCardNewsRequestDto request);
}
