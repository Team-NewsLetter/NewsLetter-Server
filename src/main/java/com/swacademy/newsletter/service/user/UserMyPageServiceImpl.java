package com.swacademy.newsletter.service.user;

import com.swacademy.newsletter.domain.user.Users;
import com.swacademy.newsletter.repository.user.UserNewsHistoryQueryRepository;
import com.swacademy.newsletter.repository.user.UserRepository;
import com.swacademy.newsletter.repository.user.UserTagStatisticsQueryRepository;
import com.swacademy.newsletter.web.dto.request.user.NicknameUpdateRequestDto;
import com.swacademy.newsletter.web.dto.response.user.NicknameUpdateResponseDto;
import com.swacademy.newsletter.web.dto.response.user.UserInfoResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMyPageServiceImpl implements UserMyPageService {

    private final UserRepository userRepository;
    private final UserNewsHistoryQueryRepository newsHistoryQueryRepository;
    private final UserTagStatisticsQueryRepository tagPreferQueryRepository;

    @Override
    public UserInfoResponseDto getUserInfo(Long userId) {

        Users user = userRepository.findUsersById(userId).
                orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        List<UserInfoResponseDto.DailyNewsCheckDto> checks = newsHistoryQueryRepository.getDailyNewsCheck(userId, 7);
        int consecutiveDays = newsHistoryQueryRepository.countConsecutiveDays(checks);
        int todayNews = newsHistoryQueryRepository.countTodayNews(userId);
        List<UserInfoResponseDto.UserTagPreferenceStaticsDto> tagStats = tagPreferQueryRepository.getDynamicUserTagPreferences(userId);

        return UserInfoResponseDto.builder()
                .nickname(user.getNickname())
                .totalNewsReadCount(user.getNewsReadingCount())
                .todayNewsReadCount(todayNews)
                .consecutiveReadDays(consecutiveDays)
                .practiceCount(user.getPracticeCount())
                .dailyNewsCheck(checks)
                .userTagPreferenceStatics(tagStats)
                .build();
    }

    @Override
    @Transactional
    public NicknameUpdateResponseDto updateNickname(Long userId, NicknameUpdateRequestDto request) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
        user.updateNickname(request.getNickname());
        return NicknameUpdateResponseDto.builder()
                .userId(user.getId())
                .nickname(request.getNickname())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}