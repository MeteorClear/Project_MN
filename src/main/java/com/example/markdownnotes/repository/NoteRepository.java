package com.example.markdownnotes.repository;

import com.example.markdownnotes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
	// 기능 추가
}
