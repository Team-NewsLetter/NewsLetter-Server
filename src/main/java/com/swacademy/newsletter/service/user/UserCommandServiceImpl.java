package com.swacademy.newsletter.service.user;

import com.swacademy.newsletter.apiPayload.code.status.ErrorStatus;
import com.swacademy.newsletter.apiPayload.exception.GeneralException;
import com.swacademy.newsletter.apiPayload.exception.handler.NewsTagHandler;
import com.swacademy.newsletter.repository.user.UserTagPreferenceRepository;
import com.swacademy.newsletter.security.JwtTokenProvider;
import com.swacademy.newsletter.converter.UserConverter;
import com.swacademy.newsletter.converter.UserPreferConverter;
import com.swacademy.newsletter.domain.user.Users;
import com.swacademy.newsletter.domain.user.UserTagPreference;
import com.swacademy.newsletter.domain.tag.NewsTag;
import com.swacademy.newsletter.repository.news.NewsTagRepository;
import com.swacademy.newsletter.repository.user.UserRepository;
import com.swacademy.newsletter.web.dto.request.user.UserRequestDto;
import com.swacademy.newsletter.web.dto.response.user.UserResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final NewsTagRepository newsTagRepository;
    private final UserTagPreferenceRepository userTagPreferenceRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public Users joinUser(UserRequestDto.SignupDto request) {

        // 1. 비밀번호 일치 여부 검증
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_MISMATCH);
        }

        // 2. 이메일 중복 여부 확인
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new GeneralException(ErrorStatus.EMAIL_DUPLICATED);
        }

        Users newUsers = UserConverter.toUser(request, passwordEncoder);

        List<NewsTag> newsTagList = request.getPreferTags().stream()
                .map(newsTag -> {
                    return newsTagRepository.findById(newsTag).orElseThrow(() -> new NewsTagHandler(ErrorStatus.NEWS_TAG_NOT_FOUND));
                }).collect(Collectors.toList());

        List<UserTagPreference> userTagPreferenceList = UserPreferConverter.toUserPreferList(newsTagList);

        userTagPreferenceList.forEach(userTagPreference -> {
            userTagPreference.setUsers(newUsers);
        });

        return userRepository.save(newUsers);
    }

    @Override
    @Transactional
    public UserResponseDto.LoginResultDto login(UserRequestDto.LoginDto request) {
        Users users = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        //
        if (!passwordEncoder.matches(request.getPassword(), users.getPassword())) {
            throw new GeneralException(ErrorStatus.INVALID_PASSWORD);
        }

        String token = jwtTokenProvider.generateToken(users.getId());
        return UserResponseDto.LoginResultDto.builder()
                .userId(users.getId())
                .nickname(users.getNickname())
                .accessToken(token)
                .build();
    }

    @Override
    @Transactional
    public void changePassword(Long userId, UserRequestDto.ChangePasswordDto request) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // 기존 비밀번호 검증
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus.INVALID_PASSWORD);
        }

        // 새 비밀번호와 비밀번호 확인이 일치하는지 검증
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_MISMATCH);
        }

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedNewPassword);

        userRepository.save(user);
    }

    @Override
    public UserResponseDto.PreferenceTagsResultDto getUserTagPreferences(
            Long userId) {
        List<UserTagPreference> preferences = userTagPreferenceRepository.findByUserId(userId);
        List<String> tagNames = preferences.stream()
                .map(p -> p.getNewsTag().getName().name())
                .collect(Collectors.toList());

        return UserResponseDto.PreferenceTagsResultDto.builder()
                .tags(tagNames)
                .build();
    }
}
