package com.example.markdownnotes.service;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.model.Note;
import com.example.markdownnotes.repository.UserRepository;
import com.example.markdownnotes.repository.NoteRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
	private final UserRepository userRepository;
	private final NoteRepository noteRepository;
	
	// 모든 메모 조회
	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}
	
	// 메모 id 로 조회
	public Optional<Note> getNoteById(Integer id) {
		return noteRepository.findById(id);
	}
	
	// 특정 사용자의 메모 목록 조회
	public List<Note> getNotesByUser(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("[ERROR]User not found, id: " + userId));
		return noteRepository.findByUser(user);
	}
	
	// 메모 생성
	public Note createNote(Note note, Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("[ERROR]User not found, id: " + userId));
		note.setUser(user);
		return noteRepository.save(note);
	}
	
	// 메모 삭제
	public void deleteNote(Integer id) {
		noteRepository.deleteById(id);
	}
	
	// 메모 업데이트
	public Note updateNote(Integer id, Note updatedNote) {
		return noteRepository.findById(id).map(note -> {
			note.setTitle(updatedNote.getTitle());
			note.setContent(updatedNote.getContent());
			note.setUpdatedAt(updatedNote.getUpdatedAt());
			return noteRepository.save(note);
		}).orElseThrow(() -> new RuntimeException("[ERROR]Note not found, id: " + id));
	}
}
