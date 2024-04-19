package com.example.peer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeerApplication.class, args);
	}

}
