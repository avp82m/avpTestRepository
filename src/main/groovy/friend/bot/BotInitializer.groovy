package friend.bot

import javax.annotation.PostConstruct
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.BotSession;

import friend.bot.executers.ExecutersFactory
import friend.bot.executers.IExecuters
import friend.bot.executers.command.DefaultExecuter
import friend.bot.executers.listener.AnswerListeners
import friend.bot.executers.listener.IAnswerListener

@Component
class BotInitializer extends TelegramLongPollingBot{
	
	private static final Logger log = LoggerFactory.getLogger(BotInitializer.class);
	
	@Override
	public String getBotToken() {
		return "492373748:AAEDUKvUsY1tpWaQ3kRVdBCaCwaFcaJHp9o";
	}
	
	@Override
	public String getBotUsername() {
		return "FaceBotTest";
	}
	
	static {
		log.info("Инициализация контекста")
		ApiContextInitializer.init();
	}

	@PostConstruct
	public void registerBot(){
		log.info("Регистрация бота")
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new BotInitializer());
			log.info("Бот успешно зарегистрирован")
		} catch (TelegramApiException e) {
			log.error(e);
		}
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		
		if (update.hasMessage()) {
			log.debug("Получено сообщение: {}",update.getMessage().getText())
			Message message = update.getMessage();
			SendMessage response = getAnswer(message);
			Long chatId = message.getChatId();
			response.setChatId(chatId);
			try {
				log.debug("Отправляем ответ({}): {}", chatId,response.getText());
				sendMessage(response);
				log.debug("Ответ успешно отправлен({})", chatId);
			} catch (TelegramApiException e) {
				log.error("Failed to send message \"{}\" to {} due to error: {}", response.getText(), chatId, e.getMessage());
			}
		}
	}

	private SendMessage execCommand(String command,Message message) {
		try {
			IExecuters executer	=	new ExecutersFactory().getExecuterForCommand(command);
			executer.setMessage(message);
			return executer.getAnswer();
		}catch(Exception e) {
			log.error("execCommand Exception: {}",e);
			return new DefaultExecuter().getErrorAnswer(message,"Не понимаю чего вы хотите, проверьте корректность введенных данных.");
		}
		
	}
	
	private SendMessage getAnswer(Message message) {
		try { 
			if("/".equals(message.getText().substring(0,1))) {
				String command	=	message.getText().split(" ")[0];
				AnswerListeners.getListener(message.getFrom().getId());
				return execCommand(command,message);
			}
			
			IAnswerListener	listener	=	 AnswerListeners.getListener(message.getFrom().getId());
			if(listener	!=	null) {
				log.debug("Сработал листенер : {}",listener.class);
				return listener.action(message);
			}
						
			
		}catch(Exception e) {
			log.error("getAnswer: {}",e);
		}
		
		return new DefaultExecuter().getErrorAnswer(message,"Не понимаю о чем Вы. Для вывода справки введите /help");
	}

	
}
