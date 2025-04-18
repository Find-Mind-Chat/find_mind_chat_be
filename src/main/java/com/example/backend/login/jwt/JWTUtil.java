package com.example.backend.login.jwt;

import com.example.backend.global.GlobalException;
import com.example.backend.global.ResponseStatus;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${jwt.secret}") String secret){
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    public String getUuid(String token){
        return parseClaims(token).get("uuid", String.class);
    }

    //토큰 추출
    private Claims parseClaims(String accessToken) {

        try{
            return Jwts.parserBuilder().
                    setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

        } catch (ExpiredJwtException e){
            throw new GlobalException(ResponseStatus.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
            throw new GlobalException(ResponseStatus.INVALID_TOKEN);
        }
    }


    public Boolean isExpired(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public String createJwt(String uuid, @Value("${EXPIRATION_TIME}")Long expiredMs){
        return Jwts.builder()
                .claim("uuid",uuid)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String getHeader(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String headerValue = request.getHeader("Authorization");
        if (headerValue != null && headerValue.startsWith("Bearer ")){
            //"Bearer " 부분 제거
            return headerValue.substring(7).trim();
        }
        throw new GlobalException(ResponseStatus.NOT_FOUND_AUTHORIZATION_HEADER);
    }
}
