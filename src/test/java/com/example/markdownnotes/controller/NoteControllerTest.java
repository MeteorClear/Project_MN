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
	
	// getNotesByUser
	
	// createNote
	
	// deleteNote
	
	// updateNote
}
