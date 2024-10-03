package com.example.markdownnotes.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5562723498485357306L;
	
	private final String jwtToken;
	
	public JwtResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public String getToken() {
		return this.jwtToken;
	}
}
