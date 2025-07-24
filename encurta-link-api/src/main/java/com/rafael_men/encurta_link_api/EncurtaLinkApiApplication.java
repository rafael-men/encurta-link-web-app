package com.rafael_men.encurta_link_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EncurtaLinkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncurtaLinkApiApplication.class, args);
	}

}
