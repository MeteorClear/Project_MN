package com.example.markdownnotes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.*;
import java.io.FileWriter;
import java.io.IOException;
import java.security.cert.X509Certificate;

/**
 * API 문서 다운로드 설정 클래스.
 * 
 * 서버 시작시 OpenAPI 문서를 자동으로 다운로드하여 JSON 파일로 저장.
 */
@Configuration
public class APIDocSaveConfig {
	
	/**
	 * OpenAPI 문서의 URL 경로.
	 */
	@Value("${springdoc.api-docs.server-url}${springdoc.api-docs.path}")
	String openAPIUrl;
	
	/**
	 * 저장할 파일 경로.
	 */
	String outputFilePath = "openapi-doc.json";
	
	/**
	 * 모든 SSL 인증서를 허용하는 RestTemplate 생성
	 * 
	 * 로컬 또는 개발 환경에서 SSL 인증서 검증 문제를 우회하기 위해 사용
	 * 
	 * @return RestTemplate 인증서 설정을 가진 객체
	 * @throws Exception 예외 처리
	 */
	@Bean
	public RestTemplate restTemplate() throws Exception {
		// SSLContext 생성
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[]{new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		
			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {}
		
			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {}
		}}, new java.security.SecureRandom());
		
		// 모든 SSL 인증서를 신뢰하도록 설정
		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
		
		return new RestTemplate();
	}

	/**
	 * API 문서를 다운로드 후 저장.
	 * 
	 * 서버 시작시 API 문서를 pretty print 된 JSON 파일로 저장.
	 * 
	 * @param restTemplate SSL 인증서 검증을 우회한 RestTemplate 객체
	 * @return ApplicationRunner 애플리케이션 시작 시 실행되는 작업
	 */
	@Bean
	public ApplicationRunner saveOpenAPIDoc(RestTemplate restTemplate) {
		return args -> {
			// OpenAPI 문서 다운로드
			String openApiDoc = restTemplate.getForObject(openAPIUrl, String.class);
			
			// JSON Pretty Print 적용
			ObjectMapper objectMapper = new ObjectMapper();
			Object json = objectMapper.readValue(openApiDoc, Object.class);
			ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
			String prettyJson = writer.writeValueAsString(json);
			
			// 파일로 저장
			try (FileWriter file = new FileWriter(outputFilePath)) {
				file.write(prettyJson);
				System.out.println("APIDocSaveConfig: OpenAPI document saved at " + outputFilePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
        };
	}
}
