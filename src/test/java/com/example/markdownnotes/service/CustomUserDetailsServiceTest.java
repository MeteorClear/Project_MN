package com.example.markdownnotes.service;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;
	
	private User user;
	
	@BeforeEach
	public void setup() {
		user = new User();
		user.setEmail("test@example.com");
		user.setPassword("testpassword");
	}
	
	// loadUserByUsername
	@Test
	public void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
		// Given
		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
		
		// When
		UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@example.com");
		
		// Then
		assertNotNull(userDetails);
		assertEquals("test@example.com", userDetails.getUsername());
		assertEquals("testpassword", userDetails.getPassword());
		
		verify(userRepository, times(1)).findByEmail("test@example.com");
	}
	
	@Test
	public void loadUserByUsername_ShouldThrowException_WhenUserDoesNotExist() {
		// Given
		when(userRepository.findByEmail("wrong@example.com")).thenReturn(Optional.empty());
		
		// Then
		assertThrows(UsernameNotFoundException.class, () -> {
			customUserDetailsService.loadUserByUsername("wrong@example.com");
		});
		
		verify(userRepository, times(1)).findByEmail("wrong@example.com");
	}
	
}
