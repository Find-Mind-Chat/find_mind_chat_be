package com.example.backend.login.service;

import com.example.backend.global.GlobalException;
import com.example.backend.global.ResponseStatus;
import com.example.backend.login.domain.Member;
import com.example.backend.login.dto.req.GoogleTokenReqDto;
import com.example.backend.login.dto.res.JWTResDto;
import com.example.backend.login.jwt.GoogleTokenVerifier;
import com.example.backend.login.jwt.JWTUtil;
import com.example.backend.login.repository.MemberRespository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberServiceImp implements MemberSerivce{

    private final MemberRespository memberRespository;
    private final JWTUtil jwtUtil;


    @Override
    public JWTResDto googleLogin(GoogleTokenReqDto googleTokenReqDto) {

        Map<String, Object> claims = GoogleTokenVerifier.verifyToken(googleTokenReqDto.getIdToken());
        if (claims == null){
            throw new GlobalException(ResponseStatus.INVALID_GOOGLE_TOKEN);
        }

        //토큰에서 사용자 정보 추출
        String email = claims.get("email").toString();
        String name = claims.get("name").toString();
        String uuid = null;

        Optional<Member> memberInfo = memberRespository.findByEmail(email);

        if (memberInfo.isPresent()){
            //현재 가입된 회원
            uuid = memberInfo.get().getUuid();
        }

        else{
            uuid = UUID.randomUUID().toString();
            memberRespository.save(Member.builder()
                    .email(email)
                    .name(name)
                    .uuid(uuid)
                    .build());
        }
        //추출한 정보를 바탕으로 자체 jwt 발급
        return JWTResDto.builder()
                .token("Bearer "+jwtUtil.createJwt(uuid, 30L * 24 * 60 * 60 * 1000))
                .build();
    }
}
