package com.example.markdownnotes.model;

import java.io.Serializable;

/**
 * JWT 응답을 위한 서식 클래스.
 * 인증 성공 후 클라이언트에 JWT 토큰 반환.
 * Serializable 인터페이스 구현, 갹체 직렬화 가능.
 */
public class JwtResponse implements Serializable {
	
	/**
	 * 직렬화를 위한 고유 ID.
	 */
	private static final long serialVersionUID = -5562723498485357306L;
	
	/**
	 * 최종적으로 발급된 JWT 토큰.
	 */
	private final String jwtToken;
	
	/**
	 * JWT 토큰을 초기화 하는 생성자.
	 * 
	 * @param jwtToken 클라이언트에 반환할 JWT 토큰
	 */
	public JwtResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	/**
	 * JWT 토큰을 반환하는 메서드.
	 * 
	 * @return 발급된 JWT 토큰
	 */
	public String getToken() {
		return this.jwtToken;
	}
}
