package com.swacademy.newsletter.domain.speechBubble;

import com.swacademy.newsletter.domain.character.Characters;
import com.swacademy.newsletter.domain.common.BaseEntity;
import com.swacademy.newsletter.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "speech_bubble")
public class SpeechBubble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;

    @Column(columnDefinition = "TEXT")
    private String message;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "character_id", nullable = false)
//    private Characters character;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Users user;
}
