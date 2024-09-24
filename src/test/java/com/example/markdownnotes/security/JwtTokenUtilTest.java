package com.example.markdownnotes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Date;

public class JwtTokenUtilTest {
	
	@InjectMocks
	private JwtTokenUtil jwtTokenUtil;
	
	@Mock
	private Claims claims;
	
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
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
	
}
