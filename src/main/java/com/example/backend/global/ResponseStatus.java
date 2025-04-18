package com.example.backend.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseStatus {

    /**
     * 200: 요청 성공
     **/
    SUCCESS(200, "요청에 성공했습니다."),

    INTERNAL_SERVER_ERROR(900, "Internal server error"),
    INVALID_GOOGLE_TOKEN( 901, "구글 토큰이 유효하지 않습니다."),
    TOKEN_EXPIRED( 902, "토큰이 만료되었습니다."),
    INVALID_TOKEN( 903, "토큰이 유효하지 않습니다."),
    INVALID_TOKEN_FORMAT( 904, "토큰 형식이 유효하지 않습니다."),
    MISSING_TOKEN_KID( 905, "토큰의 kid가 없습니다."),
    NOT_FOUND_MATCHING_KEY( 906, "매칭되는 키가 없습니다."),
    NOT_FOUND_AUTHORIZATION_HEADER( 907, "Authorization 헤더가 없습니다."),

    /**
     * 600: member
     **/

    NOT_FOUND_MEMBER(600, "회원을 찾을 수 없습니다."),
    NOT_FRIEND(601, "친구 상태가 아닙니다");

    private final int code;
    private final String message;

}
