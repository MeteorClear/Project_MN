package com.example.markdownnotes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Swagger 설정 클래스.
 * 
 * SpringDoc OpenAPI를 이용하여 Swagger 문서를 설정.
 * Swagger UI에 표시할 내용을 구성.
 */
@Configuration
public class SwaggerConfig {

	/**
	 * OpenAPI 설정 메서드.
	 * 
	 * Swagger UI 및 문서에 표시할 정보를 정의.
	 * 
	 * @return OpenAPI 객체
	 */
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				// API 정보 설정
				.info(new Info()
						.title("Project MN API")										// API 제목
						.version("0.0.1")												// API 버전
						.description("API documentation for Project MN"))				// API 설명
				
				// JWT 인증 설정
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(new Components()
						.addSecuritySchemes("bearerAuth", new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)							// HTTP 타입 인증
								.scheme("bearer")										// Bearer 토큰 사용
								.bearerFormat("JWT")));									// JWT 형식 토큰 사용
	}
}
