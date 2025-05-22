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

    private Long newsId;

    private Long tagId;
}