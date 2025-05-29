package com.swacademy.newsletter.domain.enums;

import lombok.Getter;

// 뉴스 자체의 type
@Getter
public enum CardNewsType {
    urgent("긴급"),
    practice("실천"),
    daily("일상");

    private final String displayName;

    CardNewsType(String displayName) {
        this.displayName = displayName;
    }
}
