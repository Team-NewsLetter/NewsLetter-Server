package com.swacademy.newsletter.service.user;

import com.swacademy.newsletter.web.dto.request.user.NicknameUpdateRequestDto;
import com.swacademy.newsletter.web.dto.response.user.NicknameUpdateResponseDto;
import com.swacademy.newsletter.web.dto.response.user.UserInfoResponseDto;

public interface UserMyPageService {
    UserInfoResponseDto getUserInfo(Long userId);
    NicknameUpdateResponseDto updateNickname(NicknameUpdateRequestDto request);
}
