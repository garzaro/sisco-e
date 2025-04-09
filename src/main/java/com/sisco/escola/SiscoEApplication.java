package com.sisco.escola;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.*;

@SpringBootApplication
@EnableWebMvc
public class SiscoEApplication implements WebMvcConfigurer {
	/*cors*/
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		WebMvcConfigurer.super.addCorsMappings(registry);
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
	}
	/*main*/
	public static void main(String[] args) {
		SpringApplication.run(SiscoEApplication.class, args);
	}
}
