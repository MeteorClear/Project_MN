package com.example.markdownnotes.repository;

import com.example.markdownnotes.model.Note;
import com.example.markdownnotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
	// 특정 사용자가 작성한 모든 메모 목록 조회
	List<Note> findByUser(User user);
}
