package com.swacademy.newsletter.repository.user;

import com.swacademy.newsletter.domain.mapping.UserNewsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNewsHistoryRepository extends JpaRepository<UserNewsHistory, Long> {
    Boolean existsByUserIdAndNewsId(Long userId, Long CardNewsId);
}
