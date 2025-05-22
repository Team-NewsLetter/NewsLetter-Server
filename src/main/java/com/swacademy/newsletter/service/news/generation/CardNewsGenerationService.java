package com.swacademy.newsletter.service.news.generation;

import com.swacademy.newsletter.web.dto.request.generation.GenerateCardNewsRequestDto;
import com.swacademy.newsletter.web.dto.response.cardnews.GenerateCardNewsResponseDto;

public interface CardNewsGenerationService {

    GenerateCardNewsResponseDto generateCardNews(GenerateCardNewsRequestDto request);
}
