package com.swacademy.newsletter.domain.character;

import com.swacademy.newsletter.domain.common.BaseEntity;
import com.swacademy.newsletter.domain.speechBubble.SpeechBubble;
import com.swacademy.newsletter.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "characters")
public class Characters extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nickname;

    private int level;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users user;

//    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CharactersImage> charactersImageList = new ArrayList<>();

//    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<SpeechBubble> speechBubbleList = new ArrayList<>();
}