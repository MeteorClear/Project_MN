package com.example.markdownnotes.controller;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	private User user;
	
	@BeforeEach
	void setup() {
		user = new User();
		user.setId(1);
		user.setEmail("test@example.com");
		user.setPassword("password");
		user.setUsername("testuser");
	}
	
	// getAllUsers
	@Test
	void getAllUsers_ShouldReturnUserList_WhenUsersExist() throws Exception {
		// Given
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                
        // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())));
        
        verify(userService, times(1)).getAllUsers();
	}
	
	// getUserById
	@Test
	void getUserById_ShouldReturnUser_WhenUserExists() throws Exception {
		// Given
		when(userService.getUserById(1)).thenReturn(Optional.of(user));
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1")
				.contentType(MediaType.APPLICATION_JSON))
		
		// Then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is(user.getEmail())));
		
		verify(userService, times(1)).getUserById(1);
	}
	
	// getUserByEmail
	@Test
	void getUserByEmail_ShouldReturnUser_WhenUserExists() throws Exception {
		// Given
		when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/email")
				.param("email", "test@example.com")
				.contentType(MediaType.APPLICATION_JSON))
		
		// Then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is(user.getEmail())));
		
		verify(userService, times(1)).getUserByEmail("test@example.com");
	}
	
	// createUser
	@Test
	void createUser_ShouldReturnCreatedUser() throws Exception {
		// Given
		when(userService.createUser(any(User.class))).thenReturn(user);
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"email\":\"test@example.com\", " +
						"\"password\":\"password\", " +
						"\"username\":\"testuser\"}"))
		
		// Then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is(user.getEmail())));
		
		verify(userService, times(1)).createUser(any(User.class));
	}
	
	// deleteUser
	@Test
	void deleteUser_ShouldReturnNoContent_WhenUserExists() throws Exception {
		// Given
		doNothing().when(userService).deleteUser(1);
		
		// When
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1")
				.contentType(MediaType.APPLICATION_JSON))
		
		// Then
				.andExpect(status().isNoContent());
		
		verify(userService, times(1)).deleteUser(1);
	}
	
	// updateUser
}
