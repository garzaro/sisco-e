package com.sisco_e.escola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//@EnableCaching
@SpringBootApplication
public class SiscoEApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiscoEApplication.class, args);
	}

}
