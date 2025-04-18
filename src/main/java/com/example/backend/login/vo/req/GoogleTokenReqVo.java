package com.example.backend.login.vo.req;

import com.example.backend.login.dto.req.GoogleTokenReqDto;
import lombok.Getter;

@Getter
public class GoogleTokenReqVo {

    private String idToken;

    public static GoogleTokenReqDto voToDto(String idToken) {
        return GoogleTokenReqDto.builder()
                .idToken(idToken)
                .build();
    }
}
