package com.swacademy.newsletter.service.news.generation;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageGenerationServiceImpl implements ImageGenerationService{

    private final OpenAIClient openAIClient;

    @Override
    public List<String> generateImages(String prompt, Integer count) {
        return openAIClient.generateImageList(prompt,count);
    }
}
