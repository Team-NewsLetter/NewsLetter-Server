package com.swacademy.newsletter.service.home;

import com.swacademy.newsletter.apiPayload.code.status.ErrorStatus;
import com.swacademy.newsletter.apiPayload.exception.handler.UserHandler;
import com.swacademy.newsletter.domain.character.CharactersImage;
import com.swacademy.newsletter.domain.speechBubble.SpeechBubble;
import com.swacademy.newsletter.domain.user.Users;
import com.swacademy.newsletter.repository.character.CharactersImageRepository;
import com.swacademy.newsletter.repository.character.CharactersRepository;
import com.swacademy.newsletter.repository.speechBubble.SpeechBubbleRepository;
import com.swacademy.newsletter.repository.user.UserRepository;
import com.swacademy.newsletter.web.dto.response.home.HomeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final UserRepository userRepository;
    private final CharactersImageRepository charactersImageRepository;
    private final SpeechBubbleRepository speechBubbleRepository;

    private static final String BACKGROUND_IMAGE_URL = "/images/background.png";

    @Override
    @Transactional
    public HomeResponseDto getHome(Long userId) {
        // 유저 조회
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        // 레벨 계산 = (읽은 게시물 수 / 3) + 1, 최대 10
        int newsReadingCount = user.getNewsReadingCount();
        int calculatedLevel = Math.min((newsReadingCount / 3) + 1, 10);

        // 경험치 퍼센트 계산
        double experiencePercent = ((double) newsReadingCount % 3) * 100 / 3;

        // 캐릭터 이미지 조회
        String characterImageUrl = charactersImageRepository.findById(calculatedLevel)
                .map(CharactersImage::getImageUrl)
                .orElse("/images/default.png");

        // 랜덤 말풍선 멘트 조회
        List<SpeechBubble> bubbles = speechBubbleRepository.findByLevel(calculatedLevel);
        String speech = bubbles.isEmpty()
                ? "오늘도 좋은 하루!" // 기본값
                : bubbles.get(new Random().nextInt(bubbles.size())).getMessage();

        // dto 생성 후 반환
        return HomeResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .backgroundImageUrl(BACKGROUND_IMAGE_URL)
                .level(calculatedLevel)
                .newsReadingCount(newsReadingCount)
                .practiceCount(user.getPracticeCount())
                .experienceCount(experiencePercent)
                .speechBubble(speech)
                .characterImageUrl(characterImageUrl)
                .build();
    }
}
