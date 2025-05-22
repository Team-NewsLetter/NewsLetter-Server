package com.swacademy.newsletter.repository;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardNewsRepository extends JpaRepository<CardNews, Long> {
    Slice<CardNews> findByType(CardNewsType type, Pageable pageable);
}
