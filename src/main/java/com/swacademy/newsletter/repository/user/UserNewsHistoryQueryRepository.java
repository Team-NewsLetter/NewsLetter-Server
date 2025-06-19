package com.swacademy.newsletter.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swacademy.newsletter.domain.mapping.QUserNewsHistory;
import com.swacademy.newsletter.web.dto.response.user.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.*;
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
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        ZoneId zone = ZoneId.of("Asia/Seoul");

        return IntStream.range(0, 7)
                .mapToObj(i -> {
                    LocalDate date = monday.plusDays(i);

                    ZonedDateTime zonedStart = date.atStartOfDay(zone);
                    ZonedDateTime zonedEnd = date.atTime(LocalTime.MAX).atZone(zone);

                    LocalDateTime start = zonedStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    LocalDateTime end = zonedEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                    boolean checked = query
                            .selectOne()
                            .from(newsHistory)
                            .where(newsHistory.user.id.eq(userId)
                                    .and(newsHistory.readAt.between(start, end)))
                            .fetchFirst() != null;

                    return new UserInfoResponseDto.DailyNewsCheckDto(date.getDayOfMonth(), checked, date);
                })
                .toList();
    }

    public int countConsecutiveDays(List<UserInfoResponseDto.DailyNewsCheckDto> checks) {
        List<UserInfoResponseDto.DailyNewsCheckDto> sorted = checks.stream()
                .filter(check -> check.getDate() != null)
                .sorted(Comparator.comparing(UserInfoResponseDto.DailyNewsCheckDto::getDate))
                .toList();

        int count = 0;
        LocalDate expected = null;

        for (UserInfoResponseDto.DailyNewsCheckDto check : sorted) {
            if (!Boolean.TRUE.equals(check.getChecked())) break;
            if (expected == null) {
                expected = check.getDate();
            } else if (!check.getDate().equals(expected)) {
                break;
            }
            count++;
            expected = expected.plusDays(1);
        }

        return count;
    }
}
