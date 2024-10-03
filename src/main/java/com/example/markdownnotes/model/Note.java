package com.example.markdownnotes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 시스템의 메모 엔티티
 * DB의 "Note" 테이블과 매핑
 */
@Entity
@Table(name = "Note")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Note {

	/**
	 * 메모의 고유 식별자, 기본키
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 메모의 작성자, 외래키(User Table)
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	/**
	 * 메모의 제목
	 */
	@Column(nullable = false, length = 255)
	private String title;
	
	/**
	 * 메모의 내용
	 */
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
	
	/**
	 * 메모의 생성일
	 */
	@Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
	
	/**
	 * 메모의 수정일
	 */
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	/**
	 * JPA를 위한 기본 생성자
	 */
	public Note() {
		
	}
}
