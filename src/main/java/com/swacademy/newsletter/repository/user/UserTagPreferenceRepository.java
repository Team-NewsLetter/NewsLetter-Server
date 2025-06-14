package com.swacademy.newsletter.repository.user;

import com.swacademy.newsletter.domain.user.UserTagPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTagPreferenceRepository extends JpaRepository<UserTagPreference, Long> {
    List<UserTagPreference> findByUsersId(Long userId);
}
