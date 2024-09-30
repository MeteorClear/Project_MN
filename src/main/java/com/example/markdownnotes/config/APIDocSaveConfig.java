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

@Configuration
public class APIDocSaveConfig {
	
	@Value("${springdoc.api-docs.server-url}${springdoc.api-docs.path}")
	String openAPIUrl;
	
	String outputFilePath = "openapi-doc.json";
	
	// 모든 SSL 인증서 허용 설정
	@Bean
	public RestTemplate restTemplate() throws Exception {
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
		
		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
		
		return new RestTemplate();
	}

	// 
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
