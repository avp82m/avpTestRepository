package com.telegram.bot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody

import com.telegram.bot.entity.Message
import com.telegram.bot.service.MessageService

import groovy.xml.MarkupBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/Message")
class MessageController {
	private static Logger log = LoggerFactory.getLogger(MessageController.class);
	
	@Qualifier("messageService")
	@Autowired
	MessageService messageService;
	
	
	@RequestMapping(value = "add",method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody add(@RequestBody Message message) {
		log.debug("Run method ADD: {}",message.text);
		messageService.create(message);
		return message.toString();
	}

}
