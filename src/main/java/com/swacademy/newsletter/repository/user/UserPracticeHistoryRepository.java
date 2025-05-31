package com.swacademy.newsletter.repository.user;

import com.swacademy.newsletter.domain.mapping.UserPracticeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPracticeHistoryRepository extends JpaRepository<UserPracticeHistory, Long> {
    Optional<UserPracticeHistory> findByUser_IdAndCardNews_Id(Long userId, Long newsId);
}
