package com.swacademy.newsletter.repository.news;

import com.swacademy.newsletter.domain.tag.NewsTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsTagRepository extends JpaRepository<NewsTag, Long> {
}
