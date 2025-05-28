package com.swacademy.newsletter.domain.tag;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cardnews_tags")
public class CardNewsTags {

    @EmbeddedId
    private CardNewsTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("newsId")
    @JoinColumn(name = "news_id", nullable = false)
    private CardNews cardNews;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", nullable = false)
    private NewsTag tag;
}