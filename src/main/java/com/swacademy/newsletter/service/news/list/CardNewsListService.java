package com.swacademy.newsletter.service.news.list;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsTagType;
import com.swacademy.newsletter.domain.enums.CardNewsType;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CardNewsListService {
    Slice<CardNews> getCardNewsList(CardNewsType type, Integer page, Integer size);
    Slice<CardNews> getDailyCardNewsByTag(CardNewsTagType tag, Integer page, Integer size);
    Slice<CardNews> getDailyCardNewsByUserPreference(Long userId, Integer page, Integer size);
}
