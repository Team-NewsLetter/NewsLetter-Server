package com.swacademy.newsletter.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swacademy.newsletter.domain.mapping.QUserNewsHistory;
import com.swacademy.newsletter.web.dto.response.user.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
public class UserNewsHistoryQueryRepository {

    private final JPAQueryFactory query;
    private final QUserNewsHistory newsHistory = QUserNewsHistory.userNewsHistory;

    public int countTodayNews(Long userId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);

        return Optional.ofNullable(query
                .select(newsHistory.count())
                .from(newsHistory)
                .where(newsHistory.user.id.eq(userId)
                        .and(newsHistory.readAt.between(start, end)))
                .fetchOne()).orElse(0L).intValue();
    }

    public List<UserInfoResponseDto.DailyNewsCheckDto> getDailyNewsCheck(Long userId, int days) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);

        return IntStream.range(0, 7) // 월~일
                .mapToObj(i -> {
                    LocalDate date = monday.plusDays(i);
                    LocalDateTime start = date.atStartOfDay();
                    LocalDateTime end = date.atTime(LocalTime.MAX);

                    boolean checked = query
                            .selectOne()
                            .from(newsHistory)
                            .where(newsHistory.user.id.eq(userId)
                                    .and(newsHistory.readAt.between(start, end)))
                            .fetchFirst() != null;

                    return new UserInfoResponseDto.DailyNewsCheckDto(date.getDayOfMonth(), checked);
                })
                .toList();
    }

    public int countConsecutiveDays(List<UserInfoResponseDto.DailyNewsCheckDto> checks) {
        return (int) checks.stream()
                .sorted(Comparator.comparingInt(UserInfoResponseDto.DailyNewsCheckDto::getDay))
                .takeWhile(UserInfoResponseDto.DailyNewsCheckDto::getChecked)
                .count();
    }
}
