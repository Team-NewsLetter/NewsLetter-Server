package com.swacademy.newsletter.web.dto.request.user;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NicknameUpdateRequestDto {
    private String nickname;
}