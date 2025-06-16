package com.swacademy.newsletter.domain.user;

import com.swacademy.newsletter.domain.common.BaseEntity;
import com.swacademy.newsletter.domain.tag.NewsTag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_tag_preference")
public class UserTagPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private NewsTag newsTag;

    private Float tagScore;

    public void setUsers(Users users) {
        if(this.user != null) {
            users.getUserTagPreferenceList().remove(this);
        }
        this.user = users;
        users.getUserTagPreferenceList().add(this);
    }

    public void setNewsTag(NewsTag newsTag) {
        this.newsTag = newsTag;
    }
}

