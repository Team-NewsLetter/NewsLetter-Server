package com.swacademy.newsletter.repository.user;

import com.swacademy.newsletter.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findUsersById(Long id);
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u.nickname FROM Users u WHERE u.id = :id")
    String findNicknameById(@Param("id") Long id);

    @Query("SELECT u.newsReadingCount FROM Users u WHERE u.id = :id")
    Integer findNewsReadCountById(@Param("id") Long id);

    @Query("SELECT u.practiceCount FROM Users u WHERE u.id = :id")
    Integer findPracticeCountById(@Param("id") Long id);
}