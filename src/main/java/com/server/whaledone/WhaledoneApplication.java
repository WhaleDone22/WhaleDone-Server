package com.server.whaledone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WhaledoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhaledoneApplication.class, args);
	}

}
