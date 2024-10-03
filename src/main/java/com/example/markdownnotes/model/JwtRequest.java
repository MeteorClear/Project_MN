package com.example.markdownnotes.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * JWT 인증 요청을 위한 서식 클래스.
 * 사용자명(이메일), 비밀번호 전달.
 * Serializable 인터페이스 구현, 갹체 직렬화 가능.
 */
@Getter
@Setter
public class JwtRequest implements Serializable  {
	
	/**
	 * 기본 생성자.
	 * 빈 객체로 초기화.
	 */
	public JwtRequest() {
		
    }
	
	/**
	 * 사용자명과 비밀번호를 포함한 생성자.
	 * 전달받은 값으로 사용자명과 비밀번호를 초기화.
	 * 
	 * @param username 사용자명(이메일)
	 * @param password 비밀번호
	 */
	public JwtRequest(String username, String password) {
		this.username = username;
		this.password = password;
    }
	
	/**
	 * 로그인시 사용되는 사용자명(이메일).
	 */
	private String username;
	
	/**
	 * 로그인시 사용되는 비밀번호.
	 */
	private String password;
}
