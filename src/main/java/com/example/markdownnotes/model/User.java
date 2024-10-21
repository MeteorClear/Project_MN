package com.example.markdownnotes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 시스템 사용자 엔티티
 * DB의 "User" 테이블과 매핑
 */
@Entity
@Table(name = "User")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class User {

	/**
	 * 사용자의 고유값, 가본키
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 사용자의 이메일주소
	 */
	@Column(nullable = false, unique = true, length = 255)
	private String email;
	
	/**
	 * 사용자의 암호화된 비밀번호
	 */
	@Column(nullable = false, length = 255)
	private String password;
	
	/**
	 * 사용자의 닉네임
	 */
	@Column(nullable = false, length = 32)
	private String username;
	
	/**
	 * 사용자의 계정 생성일
	 * 수정: columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP" 에서 PrePersist, updatable = false
	 */
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	/**
	 * JPA를 위한 기본 생성자
	 */
	public User() {
		
	}
	
	/**
	 * 엔티티가 처음 persist 되면 createdAt를 현재 시간으로 설정
	 */
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
