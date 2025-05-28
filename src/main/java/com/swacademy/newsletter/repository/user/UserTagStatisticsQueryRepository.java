package com.swacademy.newsletter.repository.user;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swacademy.newsletter.domain.cardnews.QCardNews;
import com.swacademy.newsletter.domain.enums.CardNewsTagType;
import com.swacademy.newsletter.domain.mapping.QUserNewsHistory;
import com.swacademy.newsletter.domain.tag.QCardNewsTags;
import com.swacademy.newsletter.domain.tag.QNewsTag;
import com.swacademy.newsletter.web.dto.response.user.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserTagStatisticsQueryRepository {

    private final JPAQueryFactory query;
    private final QUserNewsHistory newsHistory = QUserNewsHistory.userNewsHistory;
    private final QCardNewsTags cardNewsTags = QCardNewsTags.cardNewsTags;
    private final QCardNews cardNews = QCardNews.cardNews;
    private final QNewsTag newsTag = QNewsTag.newsTag;

    public List<UserInfoResponseDto.UserTagPreferenceStaticsDto> getDynamicUserTagPreferences(Long userId) {

        // Count 표현식 정의 (coalesce 처리 포함)
        NumberExpression<Long> countExpr = newsHistory.id.count().coalesce(0L);

        // 쿼리: 모든 태그 기준으로 LEFT JOIN 해서 사용자의 히스토리가 있는 경우만 count
        List<Tuple> result = query
                .select(newsTag.name, countExpr)
                .from(newsTag)
                .leftJoin(cardNewsTags).on(cardNewsTags.tag.eq(newsTag))
                .leftJoin(cardNews).on(cardNews.eq(cardNewsTags.cardNews))
                .leftJoin(newsHistory).on(newsHistory.newsId.eq(cardNews.id)
                        .and(newsHistory.user.id.eq(userId)))
                .groupBy(newsTag.name)
                .fetch();

        // 전체 count 계산
        long total = result.stream()
                .mapToLong(t -> Optional.ofNullable(t.get(countExpr)).orElse(0L))
                .sum();

        // 결과 DTO 매핑
        return result.stream()
                .map(tuple -> {
                    CardNewsTagType tagName = tuple.get(newsTag.name);
                    long count = Optional.ofNullable(tuple.get(countExpr)).orElse(0L);
                    double percentage = (total == 0) ? 0.0 : ((double) count / total) * 100;
                    double roundedPercentage = Math.round(percentage * 10) / 10.0;
                    return new UserInfoResponseDto.UserTagPreferenceStaticsDto(tagName.name(), roundedPercentage);
                })
                .toList();
    }
}

