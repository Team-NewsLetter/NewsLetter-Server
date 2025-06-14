package com.swacademy.newsletter.web.controller.users;

import com.swacademy.newsletter.apiPayload.ApiResponse;
import com.swacademy.newsletter.security.JwtTokenProvider;
import com.swacademy.newsletter.converter.UserConverter;
import com.swacademy.newsletter.domain.user.Users;
import com.swacademy.newsletter.service.user.UserCommandService;
import com.swacademy.newsletter.web.dto.request.user.UserRequestDto;
import com.swacademy.newsletter.web.dto.response.user.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ApiResponse<UserResponseDto.SignupResultDto> join(@RequestBody @Valid UserRequestDto.SignupDto request) {
        Users users = userCommandService.joinUser(request);
        return ApiResponse.onSuccess(UserConverter.toSignupResultDto(users));
    }

    @PostMapping("/login")
    public ApiResponse<UserResponseDto.LoginResultDto> login(@RequestBody @Valid UserRequestDto.LoginDto request) {
        UserResponseDto.LoginResultDto response = userCommandService.login(request);
        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/changePassword")
    public ApiResponse<?> changePassword(@RequestHeader("Authorization") String authHeader, @RequestBody UserRequestDto.ChangePasswordDto request) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        userCommandService.changePassword(userId, request);
        return ApiResponse.onSuccess("비밀번호 변경이 완료되었습니다.");
    }

    @GetMapping("/tags")
    public ApiResponse<UserResponseDto.PreferenceTagsResultDto> getUserTags(
            @AuthenticationPrincipal Long userId
    ) {
        return ApiResponse.onSuccess(userCommandService.getUserTagPreferences(userId));
    }
}
