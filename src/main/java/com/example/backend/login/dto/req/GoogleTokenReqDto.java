package com.example.backend.login.dto.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoogleTokenReqDto {

    private String idToken;
}
