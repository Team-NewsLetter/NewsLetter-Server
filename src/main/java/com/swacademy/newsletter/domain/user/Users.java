package com.swacademy.newsletter.domain.user;

import com.swacademy.newsletter.domain.common.BaseEntity;
import com.swacademy.newsletter.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String password;

    private String profileImageUrl;

    private String backgroundImageUrl;

    @Column(nullable = false)
    private Integer newsReadingCount = 0;

    @Column(nullable = false)
    private Integer serviceEnterCount = 0;

    @Column(nullable = false)
    private Integer practiceCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private UserStatus status;

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<UserTagPreference> userTagPreferenceList = new ArrayList<>();

    public void setPassword(String password) {
        this.password = password;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}

