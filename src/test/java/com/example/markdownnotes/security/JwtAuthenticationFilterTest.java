package com.example.markdownnotes.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {

	@InjectMocks
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private UserDetails userDetails;
	
	@Mock
	private UserDetailsService userDetailsService;
	
	@Mock
	private FilterChain filterChain;
	
	private String username;
	private String token;
	
	@BeforeEach
	public void setup() {
		username = "test@example.com";
		token = "test_jwt_token";
	}
	
	// doFilterInternal
	@Test
	public void doFilterInternal_ShouldSetAuthentication_WhenJwtTokenIsValid() throws Exception {
		// Given
		when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
		when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);
		when(jwtTokenUtil.validateToken(token, username)).thenReturn(true);
		
		// When
		jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
		
		// Then
		assertNotNull(SecurityContextHolder.getContext().getAuthentication());
		assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
		
		verify(jwtTokenUtil, times(1)).getUsernameFromToken(token);
		verify(jwtTokenUtil, times(1)).validateToken(token, username);
		verify(filterChain, times(1)).doFilter(request, response);
	}
}
