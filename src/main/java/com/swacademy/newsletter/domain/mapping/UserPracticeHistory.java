package com.swacademy.newsletter.domain.mapping;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsPracticeType;
import com.swacademy.newsletter.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPracticeHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private CardNews cardNews;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private CardNewsPracticeType practiceStatus;

    @Column(name = "practice_at")
    private LocalDateTime practiceAt;
}