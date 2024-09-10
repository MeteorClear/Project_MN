package com.example.markdownnotes.controller;

import com.example.markdownnotes.model.Note;
import com.example.markdownnotes.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
	private final NoteService noteService;
	
	// 모든 메모 조회
	@GetMapping
	public ResponseEntity<List<Note>> getAllNotes() {
		List<Note> notes = noteService.getAllNotes();
		return ResponseEntity.ok(notes);
	}
	
	// 메모 id 로 조회
	@GetMapping("/{id}")
	public ResponseEntity<Note> getNoteById(@PathVariable Integer id) {
		Optional<Note> note = noteService.getNoteById(id);
		return note.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	// 특정 사용자의 메모 목록 조회
	@GetMapping("user/{userId}")
	public ResponseEntity<List<Note>> getNotesByUser(@PathVariable Integer userId) {
		try {
			List<Note> notes = noteService.getNotesByUser(userId);
			return ResponseEntity.ok(notes);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	// 메모 생성
	@PostMapping("/user/{userId}")
	public ResponseEntity<Note> createNote(@RequestBody Note note, @PathVariable Integer userId) {
		try {
			Note createdNote = noteService.createNote(note, userId);
			return ResponseEntity.ok(createdNote);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	// 메모 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNote(@PathVariable Integer id) {
		noteService.deleteNote(id);
		return ResponseEntity.noContent().build();
	}
	
	// 메모 업데이트
	@PutMapping("/{id}")
	public ResponseEntity<Note> updateNote(@PathVariable Integer id, @RequestBody Note upadatedNote) {
		try {
			Note note = noteService.updateNote(id, upadatedNote);
			return ResponseEntity.ok(note);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
