package com.swacademy.newsletter.domain.cardnews;

import com.swacademy.newsletter.domain.common.BaseEntity;
import com.swacademy.newsletter.domain.enums.CardNewsType;
import com.swacademy.newsletter.domain.tag.CardNewsTags;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardNews extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false)
    private int likes;

    @Setter
    @Column(nullable = false)
    private int dislikes;

    @Column()
    private String thumbnailImageUrl;

    @OneToMany(
            mappedBy = "cardNews",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<CardNewsTags> cardNewsTags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private CardNewsType type;

    @OneToMany(
            mappedBy = "cardNews",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<CardImage> images = new ArrayList<>();
}
