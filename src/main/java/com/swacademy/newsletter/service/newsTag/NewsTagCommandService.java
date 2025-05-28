package com.swacademy.newsletter.service.newsTag;

import java.util.List;

public interface NewsTagCommandService {
    boolean existsAllByIds(List<Long> id);
}
