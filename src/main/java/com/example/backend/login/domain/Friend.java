package com.example.backend.login.domain;

import com.example.backend.login.enums.FriendType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member friend;

    private Boolean star;

    @Enumerated(EnumType.STRING)
    private FriendType status;

    @Builder
    public Friend(String uuid, Member friend, Boolean star, FriendType status) {
        this.uuid = uuid;
        this.friend = friend;
        this.star = star;
        this.status = status;
    }

}
