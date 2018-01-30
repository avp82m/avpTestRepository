package com.telegram.bot.service

import org.apache.camel.component.telegram.model.IncomingMessage
import org.apache.camel.component.telegram.service.TelegramServiceRestBotAPIAdapter
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

import com.telegram.bot.entity.Message

@Component
class MessageService {
	private static Logger log = LoggerFactory.getLogger(MessageService.class);
	
	public Boolean remove(IncomingMessage message) {
//		new TelegramServiceRestBotAPIAdapter().api.
		
		return true;
	}
}
