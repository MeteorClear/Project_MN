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
	
	// getNotesByUser
	
	// createNote
	
	// deleteNote
	
	// updateNote
}
