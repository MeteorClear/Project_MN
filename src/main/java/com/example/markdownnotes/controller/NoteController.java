package com.example.markdownnotes.controller;

import com.example.markdownnotes.model.Note;
import com.example.markdownnotes.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

/**
 * 메모 관련 API를 제공하는 컨트롤러 클래스.
 * 
 * 메모 조회, 생성, 업데이트, 삭제 등의 기능을 처리.
 */
@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
	
	/**
	 * 메모 서비스 객체.
	 * 
	 * 메모 관련 로직을 처리.
	 */
	private final NoteService noteService;
	
	/**
	 * 모든 메모를 조회하는 API.
	 * 
	 * @return 모든 메모의 리스트를 반환.
	 */
	@GetMapping
	public ResponseEntity<List<Note>> getAllNotes() {
		List<Note> notes = noteService.getAllNotes();
		return ResponseEntity.ok(notes);
	}
	
	/**
	 * Id 로 메모를 조회하는 API.
	 * 
	 * @param id 조회할 메모의 Id.
	 * @return 해당 Id 의 메모를 반환, 메모가 없을 경우 404 Not Found 반환.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Note> getNoteById(@PathVariable Integer id) {
		Optional<Note> note = noteService.getNoteById(id);
		return note.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	/**
	 * 특정 사용자의 Id 로 메모 목록을 조회하는 API.
	 * 
	 * @param userId 조회할 사용자의 Id.
	 * @return 해당 사용자의 모든 메모를 반환, 사용자가 없을 경우 404 Not Found 반환.
	 */
	@GetMapping("user/{userId}")
	public ResponseEntity<List<Note>> getNotesByUser(@PathVariable Integer userId) {
		try {
			List<Note> notes = noteService.getNotesByUser(userId);
			return ResponseEntity.ok(notes);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * 메모를 생성하는 API.
	 * 
	 * @param note 생성할 메모 정보
	 * @param userId 메모를 작성하는 사용자의 Id
	 * @return 생성된 메모를 반환, 사용자가 없을 경우 404 Not Found 반환.
	 */
	@PostMapping("/user/{userId}")
	public ResponseEntity<Note> createNote(@RequestBody Note note, @PathVariable Integer userId) {
		try {
			Note createdNote = noteService.createNote(note, userId);
			return ResponseEntity.ok(createdNote);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * 메모를 삭제하는 API.
	 * 
	 * @param id 삭제할 메모의 Id
	 * @return 삭제 성공 시 204 No Content 반환.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNote(@PathVariable Integer id) {
		noteService.deleteNote(id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * 메모를 업데이트하는 API.
	 * 
	 * @param id 업데이트할 메모의 Id
	 * @param upadatedNote 업데이트할 메모 정보
	 * @return 업데이트된 메모를 반환, 메모가 없을 경우 404 Not Found 반환.
	 */
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
