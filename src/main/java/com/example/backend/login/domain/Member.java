package com.example.backend.login.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 100)
    private String uuid;

    @NotNull
    @Column(length = 20)
    private String name;

    @NotNull
    @Column(length = 50)
    private String email;

    @Column(length = 100)
    private String proImg;


    @Builder
    public Member(String uuid, String name, String email, String proImg) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.proImg = proImg;
    }
}
