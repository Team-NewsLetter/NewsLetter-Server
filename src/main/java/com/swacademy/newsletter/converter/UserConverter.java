package com.swacademy.newsletter.converter;

import com.swacademy.newsletter.domain.user.Users;
import com.swacademy.newsletter.web.dto.request.user.UserRequestDto;
import com.swacademy.newsletter.web.dto.response.user.UserResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserConverter {
    public static UserResponseDto.SignupResultDto toSignupResultDto(Users users) {
        return UserResponseDto.SignupResultDto.builder()
                .userId(users.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Users toUser(UserRequestDto.SignupDto request, PasswordEncoder passwordEncoder) {
        return Users.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .userTagPreferenceList(new ArrayList<>())
                .build();
    }
}