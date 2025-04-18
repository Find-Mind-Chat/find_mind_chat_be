package com.example.backend.login.vo.req;

import com.example.backend.login.dto.req.FriendUuidReqDto;
import lombok.Getter;

@Getter
public class FriendUuidReqVo {

    private String friendUuid;

    public static FriendUuidReqDto voToDto(FriendUuidReqVo friendUuidReqVo) {
        return FriendUuidReqDto.builder()
                .friendUuid(friendUuidReqVo.friendUuid)
                .build();
    }
}
