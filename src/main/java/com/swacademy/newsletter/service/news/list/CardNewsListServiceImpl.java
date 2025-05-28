package com.swacademy.newsletter.service.news.list;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsType;
import com.swacademy.newsletter.repository.news.CardNewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardNewsListServiceImpl implements CardNewsListService {

    private final CardNewsRepository cardNewsRepository;
    @Override
    public Slice<CardNews> getCardNewsList(CardNewsType type, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Order.desc("createdAt"))
        );

        Slice<CardNews> cardNews =cardNewsRepository.findByType(type, pageable);
        return cardNews;
    }
}
