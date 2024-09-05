package com.example.markdownnotes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Note")
public class Note {
	// 테이블 정의
	// DB 스키마에 맞출것
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false, length = 255)
	private String title;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
	
	@Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updateddAt;
	
	// Constructor
	public Note() {
		// To do
	}
	
	// Getter & Setter
	// 아직까진 필요하지 않음, 필요시 작성
}
