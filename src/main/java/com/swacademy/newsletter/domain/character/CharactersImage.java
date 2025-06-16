package com.swacademy.newsletter.domain.character;

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
@Table(name = "characters_image")
public class CharactersImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer level;

    private String imageUrl;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "character_id", nullable = false)
//    private Characters character;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Users user;
}
