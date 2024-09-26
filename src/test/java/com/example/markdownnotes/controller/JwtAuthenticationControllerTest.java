package com.example.markdownnotes.controller;

import com.example.markdownnotes.model.JwtRequest;
import com.example.markdownnotes.model.JwtResponse;
import com.example.markdownnotes.security.JwtTokenUtil;
import com.example.markdownnotes.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationControllerTest {

	@Mock
	private AuthenticationManager authenticationManager;
	
	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
	@Mock
	private CustomUserDetailsService customUserDetailsService;
	
	@InjectMocks
	private JwtAuthenticationController jwtAuthenticationController;
	
	private JwtRequest jwtRequest;
	private UserDetails userDetails;
	
	@BeforeEach
	public void setUp() {
		jwtRequest = new JwtRequest("test@example.com", "testpassword");
		userDetails = mock(UserDetails.class);
	}
	
	// createAuthenticationToken
	@Test
	public void createAuthenticationToken_ShouldReturnToken_WhenCredentialsAreValid() throws Exception {
		// Given
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
		when(customUserDetailsService.loadUserByUsername(jwtRequest.getUsername())).thenReturn(userDetails);
		when(jwtTokenUtil.generateToken(userDetails.getUsername())).thenReturn("jwt-token");
		
		// When
		ResponseEntity<?> response = jwtAuthenticationController.createAuthenticationToken(jwtRequest);
		
		// Then
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody() instanceof JwtResponse);
		assertEquals("jwt-token", ((JwtResponse) response.getBody()).getToken());
		
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(customUserDetailsService, times(1)).loadUserByUsername(jwtRequest.getUsername());
		verify(jwtTokenUtil, times(1)).generateToken(userDetails.getUsername());
	}
}
