package com.example.backend.login.dto.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JWTResDto {

    private String token;
}
