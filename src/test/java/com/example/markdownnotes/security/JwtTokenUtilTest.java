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
	@Test
	public void getUsernameFromToken_ShouldReturnCorrectUsername_WhenTokenIsValid() {
		// When
		String username = jwtTokenUtil.getUsernameFromToken(token);
		
		// Then
		assertEquals(USERNAME, username);
	}
	
	// validateToken
	@Test
	public void validateToken_ShouldReturnTrue_WhenTokenIsValid() {
		// When
		boolean isValid = jwtTokenUtil.validateToken(token, USERNAME);
		
		// Then
		assertTrue(isValid);
	}
	
	// isTokenExpired
	@Test
	public void isTokenExpired_ShouldReturnFalse_WhenTokenIsValid() {
		// When
		boolean isExpired = ReflectionTestUtils.invokeMethod(jwtTokenUtil, "isTokenExpired", token);
		
		// Then
		assertFalse(isExpired);
	}
}
