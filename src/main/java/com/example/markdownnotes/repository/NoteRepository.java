package com.example.markdownnotes.repository;

import com.example.markdownnotes.model.Note;
import com.example.markdownnotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 메모(Note) 엔티티에 대한 데이터 접근 레이어 클래스.
 * 
 * JpaRepository를 상속받아 CRUD 기능을 제공.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
	
	/**
	 * 특정 사용자가 작성한 모든 메모 조회.
	 * 
	 * @param user 메모를 작성한 사용자(User) 엔티티
	 * @return 사용자에 의해 작성된 모든 메모(Note) 목록
	 */
	List<Note> findByUser(User user);
}
