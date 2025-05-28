package com.swacademy.newsletter.service.user;

import com.swacademy.newsletter.domain.user.Users;
import com.swacademy.newsletter.web.dto.request.user.UserRequestDto;
import com.swacademy.newsletter.web.dto.response.user.UserResponseDto;

public interface UserCommandService {
    Users joinUser(UserRequestDto.SignupDto request);
    UserResponseDto.LoginResultDto login(UserRequestDto.LoginDto request);
    void changePassword(Long userId, UserRequestDto.ChangePasswordDto request);
}
