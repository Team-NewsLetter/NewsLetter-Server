package com.swacademy.newsletter.domain.mapping;

import com.swacademy.newsletter.domain.enums.CardNewsType;
import com.swacademy.newsletter.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserNewsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "news_id", nullable = false)
    private Long newsId;

    @Enumerated(EnumType.STRING)
    @Column(name = "news_type", nullable = false)
    private CardNewsType newsType;

    @Column(name = "read_at", nullable = false)
    private LocalDateTime readAt;
}
