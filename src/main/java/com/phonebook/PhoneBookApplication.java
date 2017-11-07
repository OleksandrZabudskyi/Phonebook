package com.phonebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@SpringBootApplication
public class PhoneBookApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PhoneBookApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(PhoneBookApplication.class, args);

	}
}
