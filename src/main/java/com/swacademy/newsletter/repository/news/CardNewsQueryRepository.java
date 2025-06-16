package com.swacademy.newsletter.repository.news;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsTagType;
import com.swacademy.newsletter.domain.enums.CardNewsType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.swacademy.newsletter.domain.cardnews.QCardNews.cardNews;
import static com.swacademy.newsletter.domain.tag.QCardNewsTags.cardNewsTags;
import static com.swacademy.newsletter.domain.tag.QNewsTag.newsTag;

@Repository
@RequiredArgsConstructor
public class CardNewsQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Slice<CardNews> findByTypeAndPreferredTags(CardNewsType type, List<CardNewsTagType> tags, Pageable pageable) {

        List<CardNews> results = queryFactory
                .selectDistinct(cardNews)
                .from(cardNews)
                .join(cardNews.cardNewsTags, cardNewsTags)
                .join(cardNewsTags.tag, newsTag)
                .where(
                        cardNews.type.eq(type),
                        newsTag.name.in(tags)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // Slice를 위한 +1
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();

        if (hasNext) {
            results.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }


    public Slice<CardNews> findByTypeAndExactTag(CardNewsType type, CardNewsTagType tag, Pageable pageable) {
        List<CardNews> results = queryFactory
                .selectDistinct(cardNews)
                .from(cardNews)
                .join(cardNews.cardNewsTags, cardNewsTags)
                .join(cardNewsTags.tag, newsTag)
                .where(
                        cardNews.type.eq(type),
                        newsTag.name.eq(tag)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();
        if (hasNext) {
            results.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }
}
