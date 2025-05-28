package com.swacademy.newsletter.web.dto.request.user;

import com.swacademy.newsletter.validation.annotation.ExistNewsTags;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;


public class UserRequestDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignupDto {
        @NotBlank
        String email;
        @NotBlank
        String password;
        @NotBlank
        String confirmPassword;
        @NotBlank
        String nickname;
        @ExistNewsTags
        List<Long> preferTags;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDto {
        @NotBlank
        String email;
        @NotBlank
        String password;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangePasswordDto {
        @NotBlank
        String currentPassword;
        @NotBlank
        String newPassword;
        @NotBlank
        String confirmPassword;
    }
}
