package com.swacademy.newsletter.domain.tag;

import com.swacademy.newsletter.domain.cardnews.CardNews;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "cardnews_tags")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardNewsTags {

    /** 복합키 (news_id + tag_id) */
    @EmbeddedId
    private CardNewsTagId id;

    /** 뉴스 쪽 FK 매핑 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("newsId")
    @JoinColumn(name = "news_id", nullable = false)
    private CardNews cardNews;

    /** 태그 쪽 FK 매핑 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", nullable = false)
    private NewsTag newsTag;
}