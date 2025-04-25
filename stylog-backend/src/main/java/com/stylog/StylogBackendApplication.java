package com.stylog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.stylog")
public class StylogBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StylogBackendApplication.class, args);
	}

}
