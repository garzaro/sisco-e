package com.sisco.escola.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
	@Bean
	WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
			public void addCorsMappings(@NotNull CorsRegistry registry){
				registry.addMapping("/api/usuarios/**")
				.allowedOrigins("http://localhost:3000")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true)
                .maxAge(3600);
			}
		};
	}
}
