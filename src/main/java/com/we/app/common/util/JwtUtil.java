package com.we.app.common.util;

import com.we.app.common.BusinessException;
import com.we.app.common.login.model.Login;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "ASSEMBLE";

    /**
     * 토큰 생성
     * @param login 로그인 정보
     * @return String
     */
    public static String createToken(Login login) {
        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("memberName", login.getMemberName());

        long expiredTime = 1000 * 60L * 60L * 2L; // 토큰 유효 시간 (2시간)

        Date ext = new Date(); // 토큰 만료 시간
        ext.setTime(ext.getTime() + expiredTime);

        // 토큰 Builder
        return Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setSubject("login") // 토큰 용도
                .setExpiration(ext) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, KEY.getBytes()) // HS256과 Key로 Sign
                .compact();
    }

    //토큰 검증
    public Map<String, Object> verifyJWT(String jwt) {
        Map<String, Object> claimMap = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(KEY.getBytes(StandardCharsets.UTF_8)) // Set Key
                    .parseClaimsJws(jwt) // 파싱 및 검증, 실패 시 에러
                    .getBody();

            claimMap = claims;

        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
            throw BusinessException.create("토큰이 만료됐습니다. 다시 로그인해주세요.");
        } catch (Exception e) { // 그외 에러났을 경우
            throw BusinessException.create("로그인관련 문제가 발생했습니다.");
        }

        return claimMap;
    }

}
