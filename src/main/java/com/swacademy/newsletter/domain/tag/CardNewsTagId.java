package com.swacademy.newsletter.domain.tag;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardNewsTagId implements Serializable {
    private static final long serialVersionUID = 1L;

    /** cardnews_tags.news_id */
    private Long newsId;

    /** cardnews_tags.tag_id */
    private Long tagId;
}