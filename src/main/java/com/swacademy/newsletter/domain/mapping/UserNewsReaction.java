package com.swacademy.newsletter.domain.mapping;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import com.swacademy.newsletter.domain.enums.CardNewsReactionType;
import com.swacademy.newsletter.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_news_reaction")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserNewsReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private CardNews cardNews;

    @Enumerated(EnumType.STRING)
    private CardNewsReactionType reactionType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void changeReaction(CardNewsReactionType newReaction) {
        this.reactionType = newReaction;
        this.updatedAt = LocalDateTime.now();
    }
}
