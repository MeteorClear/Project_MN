package com.example.markdownnotes.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "User")
public class User {
	// 테이블 정의
	// DB 스키마에 맞출것
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, unique = true, length = 255)
	private String email;
	
	@Column(nullable = false, length = 255)
	private String password;
	
	@Column(nullable = false, length = 32)
	private String username;
	
	@Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
	
	// Constructor
	public User() {
		// To do
	}
	
	// Getter & Setter
	// 아직까진 필요하지 않음, 필요시 작성
}
