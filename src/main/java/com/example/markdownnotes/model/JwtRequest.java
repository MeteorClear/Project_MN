package com.example.markdownnotes.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest implements Serializable  {
	
	public JwtRequest() {
    }
	
	public JwtRequest(String username, String password) {
		this.username = username;
		this.password = password;
    }
	
	// 사용자명
	private String username;
	
	// 비밀번호
	private String password;
}