package com.example.markdownnotes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenUtil {
	
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	private static final long EXPIRATION_TIME = 86400000; // 1 day
	
	// 토큰 생성
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}
	
	// 토큰 닉네임 파싱
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
	
	// 토큰 유효성 검사
	public boolean validateToken(String token, String username) {
		String tokenUsername = getUsernameFromToken(token);
		return (username.equals(tokenUsername) && !isTokenExpired(token));
	}
	
	// 토큰 만료 검사
	private boolean isTokenExpired(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
		return claims.getExpiration().before(new Date());
	}
}
