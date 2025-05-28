package com.swacademy.newsletter.web.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NicknameUpdateResponseDto {
    private Long userId;
    private String nickname;
    private LocalDateTime updatedAt;
}
