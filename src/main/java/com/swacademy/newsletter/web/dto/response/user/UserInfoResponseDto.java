package com.swacademy.newsletter.web.dto.response.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {
    private String nickname;
    private Integer totalNewsReadCount;
    private Integer todayNewsReadCount;
    private Integer consecutiveReadDays;
    private Integer practiceCount;
    private List<DailyNewsCheckDto> dailyNewsCheck;
    private List<UserTagPreferenceStaticsDto> userTagPreferenceStatics;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyNewsCheckDto {
        private Integer day;      // 예: 1, 2
        private Boolean checked;  // 예: true, false

        @JsonIgnore
        private LocalDate date;  // 내부 연산용
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserTagPreferenceStaticsDto {
        private String tag;        // 예: "동물", "식물"
        private Double percentage; // 예: 32.0, 50.9
    }
}