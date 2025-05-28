package com.swacademy.newsletter.service.newsTag;

import com.swacademy.newsletter.repository.NewsTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsTagCommandServiceImpl implements NewsTagCommandService {
    private final NewsTagRepository newsTagRepository;

    public boolean existsAllByIds(List<Long> ids) {
        return ids.stream().allMatch(newsTagRepository::existsById);
    }
}
