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

@WebMvcTest(NoteController.class)
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
	@Test
	void getNoteById_ShouldReturnNotFound_WhenNoteDoesNotExist() throws Exception {
		// Given
		when(noteService.getNoteById(1)).thenReturn(Optional.empty());
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/1")
				.contentType(MediaType.APPLICATION_JSON))
		
		// Then
				.andExpect(status().isNotFound());
		
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
		
		// Then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].title", is(note.getTitle())));
		
		verify(noteService, times(1)).getNotesByUser(1);
	}
	@Test
	void getNotesByUser_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
		// Given
		when(noteService.getNotesByUser(1)).thenThrow(new RuntimeException());
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/user/1")
				.contentType(MediaType.APPLICATION_JSON))
		
		// Then
				.andExpect(status().isNotFound());
		
		verify(noteService, times(1)).getNotesByUser(1);
	}
	
	// createNote
	@Test
	void createNote_ShouldReturnCreatedNote_WhenUserExists() throws Exception {
		// Given
		when(noteService.createNote(any(Note.class), eq(1))).thenReturn(note);
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.post("/api/notes/user/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"Test Note\", " +
						"\"content\":\"Test Note Content.\"}"))
		// Then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is(note.getTitle())));
		
		verify(noteService, times(1)).createNote(any(Note.class), eq(1));
	}
	@Test
	void createNote_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
		// Given
		when(noteService.createNote(any(Note.class), eq(1))).thenThrow(new RuntimeException());
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.post("/api/notes/user/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"Test Note\", " +
						"\"content\":\"Test Note Content.\"}"))
		// Then
				.andExpect(status().isNotFound());
		
		verify(noteService, times(1)).createNote(any(Note.class), eq(1));
	}
	
	// deleteNote
	@Test
	void deleteNote_ShouldReturnNoContent_WhenNoteExists() throws Exception {
		// Given
		doNothing().when(noteService).deleteNote(1);
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes/1")
				.contentType(MediaType.APPLICATION_JSON))
		
		// Then
				.andExpect(status().isNoContent());
		
		verify(noteService, times(1)).deleteNote(1);
	}
	
	// updateNote
	@Test
	void updateNote_ShouldReturnUpdatedNote_WhenNoteExists() throws Exception {
		// Given
		when(noteService.updateNote(eq(1), any(Note.class))).thenReturn(note);
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.put("/api/notes/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"Updated Test Note\", " +
						"\"content\":\"Test Updated Note Content.\"}"))
		// Then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is(note.getTitle())));
		
		verify(noteService, times(1)).updateNote(eq(1), any(Note.class));
	}
}
