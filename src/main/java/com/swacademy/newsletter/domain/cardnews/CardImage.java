package com.swacademy.newsletter.domain.cardnews;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "card_image")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private CardNews cardNews;

    @Column(name = "image_url")
    private String imageUrl;

    private Integer seq;

    private String description;
}
