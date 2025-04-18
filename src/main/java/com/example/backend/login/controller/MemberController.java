package com.example.backend.login.controller;

import com.example.backend.global.ResponseEntity;
import com.example.backend.global.ResponseStatus;
import com.example.backend.global.ResponseSuccess;
import com.example.backend.login.dto.res.JWTResDto;
import com.example.backend.login.service.MemberSerivce;
import com.example.backend.login.vo.req.GoogleTokenReqVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class MemberController {

    private final MemberSerivce memberService;

    @PostMapping("/login")
    public ResponseEntity<JWTResDto> googleLogin(@RequestBody GoogleTokenReqVo googleTokenReqVo){

        return new ResponseEntity<>(ResponseSuccess.SUCCESS, memberService.googleLogin(GoogleTokenReqVo.voToDto(googleTokenReqVo.getIdToken())) );

    }

}
