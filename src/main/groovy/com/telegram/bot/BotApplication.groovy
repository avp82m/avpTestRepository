package com.telegram.bot

import com.telegram.bot.bot.Bot
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.exceptions.TelegramApiException

@SpringBootApplication
class BotApplication {

	static void main(String[] args) {	
		SpringApplication.run(BotApplication.class, args);
	}
}
