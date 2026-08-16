package com.moutii.book_community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class BookCommunityApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BookCommunityApplication.class);
		app.addListeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
			System.out.println("DB_URL = " + event.getEnvironment().getProperty("DB_URL"));
			System.out.println("DB_PORT = " + event.getEnvironment().getProperty("DB_PORT"));
			System.out.println("DB_NAME = " + event.getEnvironment().getProperty("DB_NAME"));
			System.out.println("DB_SCHEMA = " + event.getEnvironment().getProperty("DB_SCHEMA"));
		});
		app.run(args);
	}

}
