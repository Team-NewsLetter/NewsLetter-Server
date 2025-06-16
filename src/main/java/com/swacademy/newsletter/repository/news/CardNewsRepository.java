package com.swacademy.newsletter.repository.news;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsTagType;
import com.swacademy.newsletter.domain.enums.CardNewsType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardNewsRepository extends JpaRepository<CardNews, Long> {
    Slice<CardNews> findByType(CardNewsType type, Pageable pageable);
}
