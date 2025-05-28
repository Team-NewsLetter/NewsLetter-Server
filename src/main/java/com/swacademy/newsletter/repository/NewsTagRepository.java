package com.swacademy.newsletter.repository;

import com.swacademy.newsletter.domain.tag.NewsTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsTagRepository extends JpaRepository<NewsTag, Long> {
}
