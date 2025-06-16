package com.swacademy.newsletter.service.news.list;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsTagType;
import com.swacademy.newsletter.domain.enums.CardNewsType;
import com.swacademy.newsletter.domain.user.UserTagPreference;
import com.swacademy.newsletter.repository.news.CardNewsQueryRepository;
import com.swacademy.newsletter.repository.news.CardNewsRepository;
import com.swacademy.newsletter.repository.user.UserTagPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardNewsListServiceImpl implements CardNewsListService {

    private final CardNewsRepository cardNewsRepository;
    private final CardNewsQueryRepository cardNewsQueryRepository;
    private final UserTagPreferenceRepository userTagPreferenceRepository;

    @Override
    public Slice<CardNews> getCardNewsList(CardNewsType type, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Order.desc("createdAt"))
        );

        Slice<CardNews> cardNews = cardNewsRepository.findByType(type, pageable);
        return cardNews;
    }

    @Override
    public Slice<CardNews> getDailyCardNewsByTag(CardNewsTagType tag, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return cardNewsQueryRepository.findByTypeAndExactTag(CardNewsType.daily, tag, pageable);
    }

    @Override
    public Slice<CardNews> getDailyCardNewsByUserPreference(Long userId, Integer page, Integer size){
    List<UserTagPreference> preferences = userTagPreferenceRepository.findByUsersId(userId);

    List<CardNewsTagType> preferredTags = preferences.stream()
            .map(p -> p.getNewsTag().getName())
            .collect(Collectors.toList());
    return cardNewsQueryRepository.findByTypeAndPreferredTags(CardNewsType.daily, preferredTags, PageRequest.of(page, size));
    }
}
