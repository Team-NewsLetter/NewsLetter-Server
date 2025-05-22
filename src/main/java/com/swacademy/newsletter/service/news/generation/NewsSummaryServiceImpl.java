package com.swacademy.newsletter.service.news.generation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsSummaryServiceImpl implements NewsSummaryService {

    private final OpenAIClient openAIClient;

    @Override
    public List<String> summarizeAndSplit(String content, String title) {
        String prompt = """
            아래 지시사항을 반드시 지켜주세요.

            ========================
            뉴스 제목:
            %s

            뉴스 본문:
            %s
            ========================

            1) 뉴스 본문을 읽고, 핵심 내용을 5개의 문단으로 나눠서 요약하세요.  
               • 문단1: 반드시 위에 입력된 '뉴스 제목'과 **완전히 동일**하게 출력  
               • 문단2~문단5: 본문 중 핵심 정보를 간결하게 담은 요약문  
               • 각 문단 사이에는 빈 줄(“\\n\\n”)을 하나씩 넣어 구분  
            2) 출력은 순수 텍스트로만, 숫자 목록이나 마크다운, 추가 설명 없이 뼈대 문단 형태로만 작성하세요.  

            (예시)
            뉴스 제목:
            기후위기, 선택의 시간

            뉴스 본문:
            ...원본 기사...

            기후위기, 선택의 시간

            올해 5월 기온은 100년 만에 가장 높게 기록되었다. [...]  

            이상 기후 현상의 원인은 [...]  

            과거 복합 재난을 겪은 경험으로 볼 때 [...]  

            정치권은 탄소중립 공약을 구체적으로 실행해야 한다.
            """.formatted(title, content);

        String result = openAIClient.askChatGPT(prompt);

        return Arrays.stream(result.split("\\n\\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}