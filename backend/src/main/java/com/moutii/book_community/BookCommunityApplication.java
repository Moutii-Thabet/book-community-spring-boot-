package com.moutii.book_community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class BookCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookCommunityApplication.class, args);
	}

}
