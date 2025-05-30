package com.swacademy.newsletter.web.controller.users;

import com.swacademy.newsletter.apiPayload.ApiResponse;
import com.swacademy.newsletter.service.user.UserMyPageService;
import com.swacademy.newsletter.web.dto.request.user.NicknameUpdateRequestDto;
import com.swacademy.newsletter.web.dto.response.user.NicknameUpdateResponseDto;
import com.swacademy.newsletter.web.dto.response.user.UserInfoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageController {

    private final UserMyPageService userMyPageService;

    @GetMapping("/mypage")
    @Operation(summary = "마이페이지 API", description = "마이페이지 API로 사용자 정보와 뉴스 이력 및 통계를 제공합니다.")
    public ApiResponse<UserInfoResponseDto> getUserInfo(
            @AuthenticationPrincipal Long userId
    ) {
        return ApiResponse.onSuccess(userMyPageService.getUserInfo(userId));
    }

    @PostMapping("/nickname")
    @Operation(summary = "닉네임 수정 API", description = "닉네임 수정 API로 Body 내 새로운 닉네임으로 수정합니다.")
    public ApiResponse<NicknameUpdateResponseDto> updateNickname(
            @AuthenticationPrincipal Long userId,
            @RequestBody NicknameUpdateRequestDto request
    ) {

        return ApiResponse.onSuccess(userMyPageService.updateNickname(userId, request));
    }
}
