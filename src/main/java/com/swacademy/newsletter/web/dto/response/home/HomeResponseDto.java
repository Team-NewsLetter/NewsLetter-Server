package com.swacademy.newsletter.web.dto.response.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeResponseDto {
    private Long userId;
    private String nickname;
    private String backgroundImageUrl; // 배경이미지

    private int level;
    private int newsReadingCount; // 읽은 게시물 수
    private int practiceCount; // 실천 횟수
    private double experienceCount; // 막대바 채우기 위한 퍼센트

    private String speechBubble;
    private String characterImageUrl;
}
