package com.example.backend.login.service;

import com.example.backend.login.dto.req.GoogleTokenReqDto;
import com.example.backend.login.dto.res.JWTResDto;


public interface MemberSerivce {

    JWTResDto googleLogin(GoogleTokenReqDto googleTokenReqDto);
}
