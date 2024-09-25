package com.example.markdownnotes.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {

	@InjectMocks
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
	private String username;
	private String token;
	
	@BeforeEach
	public void setup() {
		username = "testuser";
		token = "Bearer test_jwt_token";
	}
}
