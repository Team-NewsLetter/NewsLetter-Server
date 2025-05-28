package com.swacademy.newsletter.converter;

import com.swacademy.newsletter.domain.user.UserTagPreference;
import com.swacademy.newsletter.domain.tag.NewsTag;
import java.util.List;
import java.util.stream.Collectors;

public class UserPreferConverter {
    public static List<UserTagPreference> toUserPreferList(List<NewsTag> newsTagList) {
        return newsTagList.stream()
                .map(newsTag ->
                        UserTagPreference.builder()
                                .newsTag(newsTag)
                                .build()
                ).collect(Collectors.toList());
    }
}