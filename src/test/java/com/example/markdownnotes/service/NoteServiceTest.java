package com.example.markdownnotes.service;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.model.Note;
import com.example.markdownnotes.repository.UserRepository;
import com.example.markdownnotes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private NoteRepository noteRepository;
	
	@InjectMocks
	private NoteService noteService;
	
	private User user;
	private Note note;
	
	@BeforeEach
	void setUp() {
		user = new User();
		user.setId(1);
		user.setEmail("test@example.com");
		user.setPassword("password");
		user.setUsername("testuser");
		
		note = new Note();
		note.setId(1);
		note.setTitle("Test Note");
		note.setContent("Test Note content");
		note.setUser(user);
	}

	// getAllNotes
	@Test
	void getAllNotes_ShouldReturnNoteList_WhenNotesExist() {
		// Given
		List<Note> notes = new ArrayList<>();
		notes.add(note);
		when(noteRepository.findAll()).thenReturn(notes);
		
		// When
		List<Note> foundNotes = noteService.getAllNotes();
		
		// Then
		assertNotNull(foundNotes);
		assertFalse(foundNotes.isEmpty());
		assertEquals(1, foundNotes.size());
		verify(noteRepository, times(1)).findAll();
	}
	
	// getNoteById
	@Test
	void getNoteById_ShouldReturnNote_WhenNoteExists() {
		// Given
		when(noteRepository.findById(1)).thenReturn(Optional.of(note));
		
		// When
		Optional<Note> foundNote = noteService.getNoteById(1);
		
		// Then
		assertTrue(foundNote.isPresent());
		assertEquals("Test Note", foundNote.get().getTitle());
		verify(noteRepository, times(1)).findById(1);
	}
	
	// getNotesByUser
	@Test
	void getNotesByUser_ShouldReturnNotes_WhenUserExists() {
		// Given
		List<Note> notes = new ArrayList<>();
		notes.add(note);
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(noteRepository.findByUser(user)).thenReturn(notes);
		
		// When
		List<Note> foundNotes = noteService.getNotesByUser(1);
		
		// Then
		assertNotNull(foundNotes);
		assertFalse(foundNotes.isEmpty());
		assertEquals(1, foundNotes.size());
		verify(noteRepository, times(1)).findByUser(user);
	}
	// RuntimeException Test getNotesByUser
	@Test
	void getNotesByUser_ShouldThrowException_WhenUserDoesNotExist() {
		// Given
		when(userRepository.findById(1)).thenReturn(Optional.empty());
		
		// Then
		assertThrows(RuntimeException.class, () -> noteService.getNotesByUser(1));
		verify(userRepository, times(1)).findById(1);
		verify(noteRepository, times(0)).findByUser(any(User.class));
	}
	
	// createNote
	@Test
	void createNote_ShouldSaveAndReturnNote_WhenUserExists() {
		// Given
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(noteRepository.save(note)).thenReturn(note);
		
		// When
		Note createdNote = noteService.createNote(note, 1);
		
		// Then
		assertNotNull(createdNote);
		assertEquals("Test Note", createdNote.getTitle());
		verify(noteRepository, times(1)).save(note);
	}
	// RuntimeException Test createNote
	@Test
	void createNote_ShouldThrowException_WhenUserDoesNotExist() {
		// Given
		when(userRepository.findById(1)).thenReturn(Optional.empty());
		
		// Then
		assertThrows(RuntimeException.class, () -> noteService.createNote(note, 1));
		verify(userRepository, times(1)).findById(1);
		verify(noteRepository, times(0)).save(any(Note.class));
	}
	
	// deleteNote
	@Test
	void deleteNote_ShouldCallRepositoryDelete_WhenNoteExists() {
		// Given
		when(noteRepository.findById(1)).thenReturn(Optional.of(note));
		
		// When
		noteService.deleteNote(1);
		
		// Then
		verify(noteRepository, times(1)).deleteById(1);
	}
	
	// updateNote
	@Test
	void updateNote_ShouldReturnUpdatedNote_WhenNoteExists() {
		// Given
		Note updatedNote = new Note();
		updatedNote.setId(1);
		updatedNote.setTitle("Updated Test Note");
		updatedNote.setContent("Updated Test Note content");
		updatedNote.setUser(user);
		
		when(noteRepository.findById(1)).thenReturn(Optional.of(note));
		when(noteRepository.save(any(Note.class))).thenReturn(updatedNote);
		
		// When
		Note result = noteService.updateNote(1, updatedNote);
		
		// Then
		assertNotNull(result);
		assertEquals("Updated Test Note", result.getTitle());
		assertEquals("Updated Test Note content", result.getContent());
		verify(noteRepository, times(1)).findById(1);
		verify(noteRepository, times(1)).save(any(Note.class));
	}
	// RuntimeException Test updateNote
	@Test
	void updateNote_ShouldThrowException_WhenNoteDoesNotExist() {
		// Given
		Note updatedNote = new Note();
		updatedNote.setId(1);
		updatedNote.setTitle("Updated Test Note");
		updatedNote.setContent("Updated Test Note content");
		
		when(noteRepository.findById(1)).thenReturn(Optional.empty());
		
		// Then
		assertThrows(RuntimeException.class, () -> noteService.updateNote(1, updatedNote));
		verify(noteRepository, times(1)).findById(1);
		verify(noteRepository, times(0)).save(any(Note.class));
	}
}
