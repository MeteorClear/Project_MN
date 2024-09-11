package com.example.markdownnotes.service;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	private User user;
	
	@BeforeEach
	void setUp() {
		user = new User();
		user.setId(1);
		user.setEmail("test@example.com");
		user.setPassword("password");
		user.setUsername("testuser");
	}
	
	// getAllUsers
	@Test
	void getAllUsers_ShouldReturnUserList_WhenUsersExist() {
		// Given
		User anotherUser = new User();
		anotherUser.setId(2);
		anotherUser.setEmail("another@example.com");
		anotherUser.setPassword("anotherpassword");
		anotherUser.setUsername("anothertestuser");
		
		when(userRepository.findAll()).thenReturn(Arrays.asList(user, anotherUser));
		
		// When
		List<User> users = userService.getAllUsers();
		
		// Then
		assertNotNull(users);
		assertEquals(2, users.size());
		verify(userRepository, times(1)).findAll();
	}
	
	// getUserById
	@Test
	void getUserById_ShouldReturnUser_WhenUserExist() {
		// Given
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		
		// When
		Optional<User> foundUser = userService.getUserById(1);
		
		// Then
		assertTrue(foundUser.isPresent());
		assertEquals("test@example.com", foundUser.get().getEmail());
	}
	
	// getUserByEmail
	@Test
	void getUserByEmail_ShouldReturnUser_WhenUserExist() {
		// Given
		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
		
		// When
		Optional<User> foundUser = userService.getUserByEmail("test@example.com");
		
		// Then
		assertTrue(foundUser.isPresent());
		assertEquals("test@example.com", foundUser.get().getEmail());
	}
	
	// createUser
	@Test
	void createUser_ShouldSaveAndReturnUser() {
		// Given
		when(userRepository.save(user)).thenReturn(user);
		
		// When
		User createdUser = userService.createUser(user);
		
		// Then
		assertNotNull(createdUser);
		assertEquals("testuser", createdUser.getUsername());
		verify(userRepository, times(1)).save(user);
	}
	
	// deleteUser
	@Test
	void deleteUser_ShouldCallRepositoryDelete_WhenUserExist() {
		// When
		userService.deleteUser(1);
		
		// Then
		verify(userRepository, times(1)).deleteById(1);
	}
	
	// updateUser
	@Test
	void updateUser_ShouldReturnUpdatedUser_WhenUserExist() {
		// Given
		User updatedUser = new User();
		updatedUser.setId(1);
		updatedUser.setEmail("updated@example.com");
		updatedUser.setPassword("updatedpassword");
		updatedUser.setUsername("updatedtestuser");
		
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(updatedUser);
		
		// When
		User result = userService.updateUser(1, updatedUser);
		
		// Then
		assertNotNull(result);
		assertEquals("updated@example.com", result.getEmail());
		assertEquals("updatedtestuser", result.getUsername());
		verify(userRepository, times(1)).findById(1);
		verify(userRepository, times(1)).save(user);
	}
	@Test
	void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
		// Given
		User updatedUser = new User();
		updatedUser.setId(1);
		updatedUser.setEmail("updated@example.com");
		updatedUser.setPassword("updatedpassword");
		updatedUser.setUsername("updatedtestuser");
		
		when(userRepository.findById(1)).thenReturn(Optional.empty());
		
		// Then
		assertThrows(RuntimeException.class, () -> userService.updateUser(1, updatedUser));
		verify(userRepository, times(1)).findById(1);
		verify(userRepository, times(0)).save(any(User.class));
	}
}
