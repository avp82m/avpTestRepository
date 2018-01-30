package com.telegram.bot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BotApplication {

	static void main(String[] args) {
		SpringApplication.run(BotApplication.class, args);
	}
}
