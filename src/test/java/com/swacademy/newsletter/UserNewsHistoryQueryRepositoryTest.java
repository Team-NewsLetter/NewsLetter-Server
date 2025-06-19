package com.swacademy.newsletter;

import com.swacademy.newsletter.repository.user.UserNewsHistoryQueryRepository;
import com.swacademy.newsletter.web.dto.response.user.UserInfoResponseDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserNewsHistoryQueryRepositoryTest {

    UserNewsHistoryQueryRepository repository = new UserNewsHistoryQueryRepository(null); // query는 사용 안 하므로 null

    private UserInfoResponseDto.DailyNewsCheckDto checked(LocalDate date) {
        return UserInfoResponseDto.DailyNewsCheckDto.builder()
                .date(date)
                .checked(true)
                .build();
    }

    private UserInfoResponseDto.DailyNewsCheckDto unchecked(LocalDate date) {
        return UserInfoResponseDto.DailyNewsCheckDto.builder()
                .date(date)
                .checked(false)
                .build();
    }

    @Test
    void 오늘만읽었을때_연속1일() {
        LocalDate today = LocalDate.now();

        List<UserInfoResponseDto.DailyNewsCheckDto> data = List.of(
                checked(today)
        );

        int result = repository.countConsecutiveDays(data);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void 어제오늘읽었을때_연속2일() {
        LocalDate today = LocalDate.now();

        List<UserInfoResponseDto.DailyNewsCheckDto> data = List.of(
                checked(today.minusDays(1)),
                checked(today)
        );

        int result = repository.countConsecutiveDays(data);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void 어제안읽고오늘만읽었을때_연속1일() {
        LocalDate today = LocalDate.now();

        List<UserInfoResponseDto.DailyNewsCheckDto> data = List.of(
                unchecked(today.minusDays(1)),
                checked(today)
        );

        int result = repository.countConsecutiveDays(data);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void 오늘안읽었을때_연속0일() {
        LocalDate today = LocalDate.now();

        List<UserInfoResponseDto.DailyNewsCheckDto> data = List.of(
                checked(today.minusDays(3)),
                checked(today.minusDays(2)),
                checked(today.minusDays(1)),
                unchecked(today)
        );

        int result = repository.countConsecutiveDays(data);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void 연속일_중간에끊김_후에오늘읽음_연속1일() {
        LocalDate today = LocalDate.now();

        List<UserInfoResponseDto.DailyNewsCheckDto> data = List.of(
                checked(today.minusDays(3)),
                unchecked(today.minusDays(2)),  // 끊김
                checked(today)
        );

        int result = repository.countConsecutiveDays(data);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void 미래날짜는무시된다() {
        LocalDate today = LocalDate.now();

        List<UserInfoResponseDto.DailyNewsCheckDto> data = List.of(
                checked(today),
                checked(today.plusDays(1)),
                checked(today.plusDays(2))
        );

        int result = repository.countConsecutiveDays(data);
        assertThat(result).isEqualTo(1); // 오늘만 계산
    }
}