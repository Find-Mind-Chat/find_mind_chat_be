package com.example.backend.login.service;

import com.example.backend.login.dto.req.FriendUuidReqDto;
import com.example.backend.login.dto.res.FriendInfoResDto;
import com.example.backend.login.vo.req.FriendUuidReqVo;

import java.util.List;

public interface FriendService {
    List<FriendInfoResDto> friendList(String uuid, String type);

    void friendRequest(String uuid, FriendUuidReqDto friendUuidReqDto);

    void friendDelete(String uuid, FriendUuidReqDto friendUuidReqDto);

    void friendAccept(String uuid, FriendUuidReqDto friendUuidReqDto);

    void friendStar(String uuid, boolean is, FriendUuidReqDto friendUuidReqDto);

    List<FriendInfoResDto> friendStarList(String uuid);
}
