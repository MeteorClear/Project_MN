package com.example.markdownnotes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenUtilTest {
	
	@InjectMocks
	private JwtTokenUtil jwtTokenUtil;
	
	@Mock
	private Claims claims;
	
	private String SECRET_KEY = "test_secret_key";
	
	private static final long EXPIRATION_TIME = 86400000;
	private final String USERNAME = "test@example.com";
	private String token;
	
	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(jwtTokenUtil, "SECRET_KEY", SECRET_KEY);
		token = Jwts.builder()
				.setSubject(USERNAME)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}
	
	// generateToken
	@Test
	public void generateToken_ShouldReturnValidToken_WhenUsernameIsProvided() {
		// When
		String generatedToken = jwtTokenUtil.generateToken(USERNAME);
		
		// Then
		assertNotNull(generatedToken);
		assertTrue(generatedToken.length() > 0);
	}
	
	// getUsernameFromToken
	
	// validateToken
	
	// isTokenExpired
}
