package com.example.markdownnotes.service;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.model.Note;
import com.example.markdownnotes.repository.UserRepository;
import com.example.markdownnotes.repository.NoteRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

/**
 * 메모 관리 서비스 클래스.
 * 
 * 메모의 조회, 생성, 업데이트, 삭제와 같은 메모 관리 기능을 담당.
 */
@Service
@RequiredArgsConstructor
public class NoteService {
	
	/**
	 * 사용자 레퍼지토리 인터페이스.
	 * 
	 * DB와 상호작용하여 사용자 정보 관리.
	 */
	private final UserRepository userRepository;
	
	/**
	 * 메모 레퍼지토리 인터페이스.
	 * 
	 * DB와 상호작용하여 메모 정보 관리.
	 */
	private final NoteRepository noteRepository;
	
	/**
	 * 모든 메모 조회.
	 * 
	 * @return List<Note> 전체 메모 목록
	 */
	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}
	
	/**
	 * 메모 Id 로 조회.
	 * 
	 * @param id 조회할 메모의 Id
	 * @return Optional<Note> Id 에 해당하는 메모 정보
	 */
	public Optional<Note> getNoteById(Integer id) {
		return noteRepository.findById(id);
	}
	
	/**
	 * 특정 사용자의 메모 목록 조회.
	 * 
	 * 사용자의 Id 기반으로 메모 목록 조회.
	 * 
	 * @param userId 조회할 사용자의 Id
	 * @return List<Note> 해당 사용자가 작성한 메모 목록
	 * @throws RuntimeException 사용자 Id 가 잘못된 경우 예외 처리
	 */
	public List<Note> getNotesByUser(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("[ERROR]User not found, id: " + userId));
		return noteRepository.findByUser(user);
	}
	
	/**
	 * 메모 생성.
	 * 
	 * @param note 생성할 메모 정보
	 * @param userId 메모를 작성한 사용자의 Id
	 * @return Note 생성된 메모 정보
	 * @throws RuntimeException 사용자 Id 가 잘못된 경우 예외 처리
	 */
	public Note createNote(Note note, Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("[ERROR]User not found, id: " + userId));
		note.setUser(user);
		return noteRepository.save(note);
	}
	
	/**
	 * 메모 삭제.
	 * 
	 * @param id 삭제할 메모의 Id
	 */
	public void deleteNote(Integer id) {
		noteRepository.deleteById(id);
	}
	
	/**
	 * 메모 업데이트.
	 * 
	 * @param id 업데이트할 메모의 Id
	 * @param updatedNote 업데이트할 메모 정보
	 * @return Note 업데이트된 메모 정보
	 * @throws RuntimeException 메모 Id 가 잘못된 경우 예외 처리
	 */
	public Note updateNote(Integer id, Note updatedNote) {
		return noteRepository.findById(id).map(note -> {
			note.setTitle(updatedNote.getTitle());
			note.setContent(updatedNote.getContent());
			note.setUpdatedAt(updatedNote.getUpdatedAt());
			return noteRepository.save(note);
		}).orElseThrow(() -> new RuntimeException("[ERROR]Note not found, id: " + id));
	}
}
