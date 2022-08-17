package com.webrtc.videoChattingService.jwt;

import com.webrtc.videoChattingService.entity.member.Member;
import com.webrtc.videoChattingService.entity.member.MemberVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 10;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //토큰이 유효한지 검사 한후 토큰에 담긴 payload를 가져온다.
    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //추출한 payload로부터 username을 가져온다
    public String getUsername(String token) {
        return extractAllClaims(token).get("nickName", String.class);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(Member member) {
        return doGenerateToken(member.getNickName(), TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(MemberVo memberVo) {
        return doGenerateToken(memberVo.getNickName(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    //토큰 생성 페이로드에 담길 값은 nickName
    public String doGenerateToken(String nickName, long expireTime) {

        Claims claims = Jwts.claims();
        claims.put("nickName", nickName);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY),SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}