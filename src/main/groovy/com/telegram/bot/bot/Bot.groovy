package com.telegram.bot.bot

import java.time.Instant;
import javax.ws.rs.NotFoundException
import org.apache.camel.component.telegram.TelegramService
import org.apache.camel.component.telegram.TelegramServiceProvider
import org.apache.camel.component.telegram.model.IncomingMessage
import org.apache.camel.component.telegram.model.OutgoingMessage
import org.apache.camel.component.telegram.model.OutgoingTextMessage
import org.apache.camel.component.telegram.model.UpdateResult
import org.apache.camel.component.telegram.service.TelegramServiceRestBotAPIAdapter
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import com.telegram.bot.cache.CacheManager
import com.telegram.bot.entity.FriendUser
import com.telegram.bot.executers.ExecutersFactory;
import com.telegram.bot.executers.IExecuters
import com.telegram.bot.executers.command.DefaultExecuter
import com.telegram.bot.executers.listener.AnswerListeners
import com.telegram.bot.executers.listener.IAnswerListener
import com.telegram.bot.service.UserService

@Component
class Bot {
	private static final Logger log = LoggerFactory.getLogger(Bot.class);
	private Long offset		=	0L;
	
	@Autowired
	private Environment env;
	
	private HashMap<String,String>botCommands = new HashMap<String,String>();
	
	@Scheduled(fixedDelay  = 1000L)
	public void loadUpdates() {
		try {
			UpdateResult res = new TelegramServiceRestBotAPIAdapter().getUpdates(env.getProperty("bot.token"), offset+1, null, null);
			log.debug("Получено сообщений: {}",res.getUpdates().size());
			res.getUpdates().each(){
				offset	=	it.getUpdateId();
				log.debug("Обрабатываем сообщение: {}",it.getMessage())
				createAnswerForMessage(it.getMessage());
			}
		}catch(NotFoundException e) {
			log.error("Exception: {}",e.getLocalizedMessage())
		};
	}
	
	
	private OutgoingTextMessage execCommand(String command,IncomingMessage message) {
		try {
			IExecuters executer	=	new ExecutersFactory().getExecuterForCommand(command);
			executer.setMessage(message);
			return executer.getAnswer();
		}catch(Exception e) {
			log.error("execCommand Exception: {}",e);
			return new DefaultExecuter().getErrorAnswer(message,"Не понимаю чего вы хотите, проверьте корректность введенных данных.");
		}
		
	}
	
	private OutgoingTextMessage getAnswer(IncomingMessage message) {
		try { 
			if("/".equals(message.getText().substring(0,1))) {
				String command	=	message.getText().split(" ")[0];
				AnswerListeners.getListener(message.from.getId());
				return execCommand(command,message);
			}
			
			IAnswerListener	listener	=	 AnswerListeners.getListener(message.from.getId());
			if(listener	!=	null) {
				log.debug("Сработал листенер : {}",listener.class);
				return listener.action(message);
			}
						
			
		}catch(Exception e) {
			log.error("getAnswer: {}",e);
		}
		
		return new DefaultExecuter().getErrorAnswer(message,"Не понимаю о чем Вы. Для вывода справки введите /help");
	}
	
	
	private Boolean createAnswerForMessage(IncomingMessage message) {
		Long userId	=	message.getFrom().getId();
		OutgoingTextMessage outMessage	=	getAnswer(message);
		log.debug("Ответ: {}",outMessage);
		outMessage.setChatId(message.getChat().getId());
		outMessage.setParseMode("html");
		new TelegramServiceRestBotAPIAdapter().sendMessage(env.getProperty("bot.token"), outMessage);
		return true;
	}
	
}
