package com.example.markdownnotes.controller;

import com.example.markdownnotes.model.JwtRequest;
import com.example.markdownnotes.security.JwtTokenUtil;
import com.example.markdownnotes.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
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
	
	@Mock
	private JwtAuthenticationController jwtAuthenticationController;
	
	private JwtRequest jwtRequest;
	private UserDetails userDetails;
	
	@BeforeEach
	public void setUp() {
		jwtRequest = new JwtRequest("test@example.com", "testpassword");
		userDetails = mock(UserDetails.class);
	}
}
