package com.example.backend.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseSuccess {

    SUCCESS(200, "요청에 성공했습니다."),
    FRIEND_LIST_SUCCESS(201, "리스트를 불러왔습니다."),
    FRIEND_REQUEST_SUCCESS(204, "친구 요청을 보냈습니다."),
    FRIEND_DELETE_SUCCESS(203, "친구를 삭제했습니다."),
    FRIEND_ACCEPT_SUCCESS(202, "친구 요청을 수락했습니다."),
    FRIEND_REJECT_SUCCESS(205, "친구 요청을 거절했습니다."),
    FRIEND_STAR_SUCCESS(206, "즐겨찾기 설정을 완료했습니다."),
    FREIND_STAR_LIST_SUCCESS(208, "즐겨찾기 친구 리스트를 불러왔습니다.");




    private final int code;
    private final String message;

}
