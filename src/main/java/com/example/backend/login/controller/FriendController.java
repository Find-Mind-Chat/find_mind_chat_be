package com.example.backend.login.controller;

import com.example.backend.global.ResponseStatus;
import com.example.backend.login.dto.res.FriendInfoResDto;
import com.example.backend.login.jwt.dto.CustomUserDetails;
import com.example.backend.login.service.FriendService;
import com.example.backend.global.ResponseEntity;
import com.example.backend.global.ResponseSuccess;
import com.example.backend.login.vo.req.FriendUuidReqVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend")
public class FriendController {

    private final FriendService friendService;

    //친구 리스트, 요청목록, 보낸목록
    @GetMapping("/list")
    public ResponseEntity<List<FriendInfoResDto>> friendList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(value = "type", required = false, defaultValue = "all") String type) {

        String uuid = customUserDetails.getUsername();
        return new ResponseEntity<>(ResponseSuccess.FRIEND_LIST_SUCCESS, friendService.friendList(uuid, type));
    }

    //친구 신청
    @PostMapping("/request")
    public ResponseEntity<Void> friendRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody FriendUuidReqVo friendUuidReqVo) {
        String uuid = customUserDetails.getUsername();
        friendService.friendRequest(uuid, FriendUuidReqVo.voToDto(friendUuidReqVo));

        return new ResponseEntity<>(ResponseSuccess.FRIEND_REQUEST_SUCCESS);
    }

    //친구 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<Void> friendDelete(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody FriendUuidReqVo friendUuidReqVo){

        String uuid = customUserDetails.getUsername();
        friendService.friendDelete(uuid, FriendUuidReqVo.voToDto(friendUuidReqVo));

        return new ResponseEntity<>(ResponseSuccess.FRIEND_DELETE_SUCCESS);
    }

    //친구 요청 수락
    @PutMapping("/accept")
    public ResponseEntity<Void> friendAccept(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody FriendUuidReqVo friendUuidReqVo){

        String uuid = customUserDetails.getUsername();
        friendService.friendAccept(uuid, FriendUuidReqVo.voToDto(friendUuidReqVo));

        return new ResponseEntity<>(ResponseSuccess.FRIEND_ACCEPT_SUCCESS);
    }

    //친구 요청 거절
    @PutMapping("/reject")
    public ResponseEntity<Void> friendReject(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody FriendUuidReqVo friendUuidReqVo){

        String uuid = customUserDetails.getUsername();
        friendService.friendDelete(uuid, FriendUuidReqVo.voToDto(friendUuidReqVo));

        return new ResponseEntity<>(ResponseSuccess.FRIEND_REJECT_SUCCESS);
    }

    //즐겨찾기 등록/해제
    @PutMapping("/star")
    public ResponseEntity<Void> friendStar(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam (value="is", required = false, defaultValue = "true") boolean is,
            @RequestBody FriendUuidReqVo friendUuidReqVo){

        String uuid = customUserDetails.getUsername();
        friendService.friendStar(uuid, is,FriendUuidReqVo.voToDto(friendUuidReqVo));

        return new ResponseEntity<>(ResponseSuccess.FRIEND_STAR_SUCCESS);
    }

    //즐겨찾기 목록 조회
    @GetMapping("/star/list")
    public ResponseEntity<List<FriendInfoResDto>> friendStarList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String uuid = customUserDetails.getUsername();

        return new ResponseEntity<>(ResponseSuccess.FREIND_STAR_LIST_SUCCESS, friendService.friendStarList(uuid));
    }



}
