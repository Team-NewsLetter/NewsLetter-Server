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
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardNews extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 500)
    private String summary;

    @Column(nullable = false)
    private int likes;

    @Column(nullable = false)
    private int dislikes;

    @Column(nullable = true)
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
}
