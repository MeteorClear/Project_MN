package com.example.markdownnotes.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User")
@Getter
@Setter
public class User {
	// 테이블 정의
	// DB 스키마에 맞출것
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;										// 기본 키
	
	@Column(nullable = false, unique = true, length = 255)
	private String email;									// 사용자 이메일
	
	@Column(nullable = false, length = 255)
	private String password;								// 암호화된 사용자 비밀번호
	
	@Column(nullable = false, length = 32)
	private String username;								// 사용자 닉네임
	
	@Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;						// 계정 생성일
	
	// Constructor
	public User() {
		// To do
	}
}
