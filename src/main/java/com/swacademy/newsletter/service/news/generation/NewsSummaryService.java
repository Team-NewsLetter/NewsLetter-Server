package com.swacademy.newsletter.service.news.generation;

import java.util.List;

public interface NewsSummaryService {
    List<String> summarizeAndSplit(String content, String title);
}
