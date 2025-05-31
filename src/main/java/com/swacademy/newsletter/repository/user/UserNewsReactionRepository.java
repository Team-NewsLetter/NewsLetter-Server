package com.swacademy.newsletter.repository.user;

import com.swacademy.newsletter.domain.mapping.UserNewsReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserNewsReactionRepository extends JpaRepository<UserNewsReaction, Long> {
    Optional<UserNewsReaction> findByUser_IdAndCardNews_Id(Long userId, Long newsId);
}
