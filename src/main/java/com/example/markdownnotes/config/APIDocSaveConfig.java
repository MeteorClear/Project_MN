package com.example.markdownnotes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import java.io.FileWriter;
import java.io.IOException;

@Configuration
public class APIDocSaveConfig {
	
	@Autowired
	private OpenAPI openAPI;
	
	public void saveOpenAPIDoc() {
		try (FileWriter file = new FileWriter("openapi-doc.json")) {
			file.write(openAPI.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
