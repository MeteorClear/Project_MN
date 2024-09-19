package com.example.markdownnotes.controller;

import com.example.markdownnotes.model.Note;
import com.example.markdownnotes.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NoteControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private NoteService noteService;
	
	private Note note;
	
	@BeforeEach
	void setup() {
		note = new Note();
		note.setId(1);
		note.setTitle("Test Note");
		note.setContent("Test Note Content.");
	}
	
	// getAllNotes
	@Test
	void getAllNotes_ShouldReturnNoteList() throws Exception {
		// Given
		when(noteService.getAllNotes()).thenReturn(Arrays.asList(note));
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.get("/api/notes")
				.contentType(MediaType.APPLICATION_JSON))
		
		// Then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].title", is(note.getTitle())));
		
		verify(noteService, times(1)).getAllNotes();
	}
	
	// getNoteById
	@Test
	void getNoteById_ShouldReturnNote_WhenNoteExists() throws Exception {
		// Given
		when(noteService.getNoteById(1)).thenReturn(Optional.of(note));
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/1")
				.contentType(MediaType.APPLICATION_JSON))
		
		// Then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is(note.getTitle())));
		
		verify(noteService, times(1)).getNoteById(1);
	}
	
	// getNotesByUser
	@Test
	void getNotesByUser_ShouldReturnNoteList_WhenUserExists() throws Exception {
		// Given
		when(noteService.getNotesByUser(1)).thenReturn(Arrays.asList(note));
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/user/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].title", is(note.getTitle())));
		
		verify(noteService, times(1)).getNotesByUser(1);
	}
	
	// createNote
	
	// deleteNote
	
	// updateNote
}
