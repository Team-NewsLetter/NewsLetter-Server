package com.swacademy.newsletter.domain.enums;

import lombok.Getter;

// 하나의 게시글에 들어갈 수 있는 관련 내용 Tag
@Getter
public enum CardNewsTagType {
    animal("동물"),
    plant("식물"),
    food("음식"),
    policy("정책"),
    science("과학"),
    life("생활");

    private final String displayName;

    CardNewsTagType(String displayName) {
        this.displayName = displayName;
    }
}