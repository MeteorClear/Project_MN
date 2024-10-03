package com.example.markdownnotes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * JWT(Json Web Token) 토큰과 관련한 유틸리티 클래스.
 * 
 * JWT 토큰을 생성, 파싱, 유효성 검사, 인증 정보 처리 담당.
 */
@Component
public class JwtTokenUtil {
	
	/**
	 * JWT 서명을 위한 비밀 키.
	 */
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	/**
	 * JWT 토큰의 만료 시간.
	 * 
	 * (1일, 86400000ms).
	 */
	private static final long EXPIRATION_TIME = 86400000; // 1 day
	
	/**
	 * JWT 토큰 생성.
	 * 
	 * 사용자 이름을 기반으로 토큰 생성.
	 * 발행일, 만료일이 포함되고 비밀키로 서명됨.
	 * 
	 * @param username 생성할 토큰의 사용자 이름
	 * @return 생성된 JWT 토큰
	 */
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)														// 토큰의 주체(사용자명) 설정
				.setIssuedAt(new Date())													// 토큰 발행 시간
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))		// 만료 시간 설정
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)								// 비밀 키로 서명
				.compact();
	}
	
	/**
	 * JWT 토큰에서 사용자 이름 추출.
	 * 
	 * @param token JWT 토큰
	 * @return 토큰에서 추출한 사용자 이름
	 */
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)				// 서명 검증을 위한 비밀 키를 설정
				.parseClaimsJws(token)					// 토큰 파싱 후 클레임 추출
				.getBody();
		return claims.getSubject();						// 주체(사용자명) 반환
	}
	
	/**
	 * JWT 토큰의 유효성 검사.
	 * 
	 * 토큰에서 추출한 사용자명과 입력된 사용자명이 일치하는지 검사.
	 * 토큰이 만료되었는지 검사.
	 * 
	 * @param token 검사할 JWT 토큰
	 * @param username 토큰을 비교할 사용자 이름
	 * @return 토큰이 유효하면 true, 그렇지 않으면 false
	 */
	public boolean validateToken(String token, String username) {
		String tokenUsername = getUsernameFromToken(token);
		return (username.equals(tokenUsername) && !isTokenExpired(token));
	}
	
	/**
	 * JWT 토큰이 만료되었는지 검사.
	 * 
	 * 만료 시간을 확인하여 만료되었는지 확인.
	 * 
	 * @param token 검사할 JWT 토큰
	 * @return 토큰이 만료되었으면 true, 그렇지 않으면 false
	 */
	private boolean isTokenExpired(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)						// 서명 검증을 위한 비밀 키를 설정
				.parseClaimsJws(token)							// 토큰을 파싱하여 클레임을 추출
				.getBody();
		return claims.getExpiration().before(new Date()); 		// 만료일과 현재 시간을 비교
	}
}
