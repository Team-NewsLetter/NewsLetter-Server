package com.swacademy.newsletter.web.controller.users;

import com.swacademy.newsletter.apiPayload.ApiResponse;
import com.swacademy.newsletter.service.user.UserMyPageService;
import com.swacademy.newsletter.web.dto.request.user.NicknameUpdateRequestDto;
import com.swacademy.newsletter.web.dto.response.user.NicknameUpdateResponseDto;
import com.swacademy.newsletter.web.dto.response.user.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageController {

    private final UserMyPageService userMyPageService;

    @GetMapping("/mypage")
    public ApiResponse<UserInfoResponseDto> getUserInfo(
            @AuthenticationPrincipal Long userId
    ) {
        return ApiResponse.onSuccess(userMyPageService.getUserInfo(userId));
    }

    @PostMapping("/nickname")
    public ApiResponse<NicknameUpdateResponseDto> updateNickname(
            @AuthenticationPrincipal Long userId,
            @RequestBody NicknameUpdateRequestDto request
    ) {

        return ApiResponse.onSuccess(userMyPageService.updateNickname(userId, request));
    }
}
