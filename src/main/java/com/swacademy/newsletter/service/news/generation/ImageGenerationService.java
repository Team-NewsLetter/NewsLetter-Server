package com.swacademy.newsletter.service.news.generation;

import java.util.List;

public interface ImageGenerationService {
    List<String> generateImages(String prompt, Integer count);
}
