package com.example.backend.login.jwt;

import com.example.backend.global.GlobalException;
import com.example.backend.global.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GoogleTokenVerifier {

    private static final String GOOGLE_JWKS_URL = "https://www.googleapis.com/oauth2/v3/certs";

    public static Claims verifyToken(String googleIdToken){
        try {
            // 1. 토큰 헤더에서 'kid' 추출
            String[] parts = googleIdToken.split("\\.");
            if (parts.length < 2) {
                throw new GlobalException(ResponseStatus.INVALID_TOKEN_FORMAT);
            }
            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> header = mapper.readValue(headerJson, Map.class);
            String kid = (String) header.get("kid");
            if (kid == null) {
                throw new GlobalException(ResponseStatus.MISSING_TOKEN_KID);
            }

            // 2. 구글 JWKS 엔드포인트에서 공개키 정보 가져오기
            URL url = new URL(GOOGLE_JWKS_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String jwksJson = in.lines().collect(Collectors.joining());
            in.close();

            Map<String, Object> jwks = mapper.readValue(jwksJson, Map.class);
            List<Map<String, Object>> keys = (List<Map<String, Object>>) jwks.get("keys");

            // kid와 일치하는 키 찾기
            Map<String, Object> matchingKey = keys.stream()
                    .filter(key -> kid.equals(key.get("kid")))
                    .findFirst()
                    .orElseThrow(() -> new GlobalException(ResponseStatus.NOT_FOUND_MATCHING_KEY));

            // 3. RSA 공개키 생성 (모듈러스 n, 지수 e)
            String n = (String) matchingKey.get("n");
            String e = (String) matchingKey.get("e");
            BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(n));
            BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(e));

            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                    .generatePublic(new RSAPublicKeySpec(modulus, exponent));

            // 4. 구글 공개키를 사용하여 토큰 서명 및 클레임 검증
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(googleIdToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new GlobalException(ResponseStatus.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new GlobalException(ResponseStatus.INVALID_GOOGLE_TOKEN);
        } catch (Exception e) {
            throw new GlobalException(ResponseStatus.INVALID_TOKEN);
        }
    }

}
