package friend.bot

import java.io.IOException

import javax.annotation.PostConstruct
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.api.objects.CallbackQuery
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.BotSession;

import friend.bot.executers.ExecutersFactory
import friend.bot.executers.IExecuters
import friend.bot.executers.command.DefaultExecuter
import friend.bot.executers.listener.UserListeners
import friend.bot.telegram.api.ActionTypes
import friend.bot.telegram.api.IAction
import friend.bot.telegram.api.Response
import friend.bot.executers.listener.IAnswerListener
import friend.bot.executers.listener.ICallbackListener

@Component
class BotInitializer extends TelegramLongPollingBot{
	public static TelegramBotsApi telegramBotsApi	=	null;
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
		telegramBotsApi = new TelegramBotsApi();
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
		}else if (update.hasCallbackQuery()) {
			log.debug("Получен callback({}): {}",update.getCallbackQuery().getMessage().getChatId(),update.toString());
			ICallbackListener	listener	=	 UserListeners.getCallbackListener(update.getCallbackQuery().getMessage().getChat().getId());
			if(listener	!=	null) {
				Response response = listener.action(update.getCallbackQuery());
				
				if(response!=null) {
					if(response.get().size>0) {
						for(IAction action:response.get()) {
							log.debug("Формируем ответ({}): {}",update.getCallbackQuery().getMessage().getChatId(),action.getType());
							switch(action.getType()) {
								case ActionTypes.DELETE:
									DeleteMessage message	=	action.getDeleteMessage()
									log.debug("Удаляем сообщение: {}",message.toString())
									deleteMessage(message);
									break;
								case ActionTypes.SENDMESSAGE: 
									SendMessage message	=	action.getSendMessage();
									message.setChatId(update.getCallbackQuery().getMessage().getChatId())
									log.debug("Отправляем сообщение: {}",message.toString());
									sendMessage(action.getSendMessage())
									break;
								case ActionTypes.EDITTEXT:
									EditMessageText message	=	action.getEditMessage() ;
									message.setChatId(update.getCallbackQuery().getMessage().getChatId())
									message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
									log.debug("Редактируем сообщение: {}",message.toString() );
									execute(message);
									break;
								default:
									log.error("Неизвестное действие")
							}
						}
					}
				}
				return;
			}else {
				DeleteMessage message	=	new DeleteMessage();
				message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
				message.setMessageId(update.getCallbackQuery().getMessage().getMessageId())
				log.debug("Удаляем устаревшее сообщение: {}",message.toString())
				deleteMessage(message);
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
			log.debug("Обрабатываем сообщение");
			if("/".equals(message.getText().substring(0,1))) {
				String command	=	message.getText().split(" ")[0];
				UserListeners.clear(message.getFrom().getId().toLong());
				return execCommand(command,message);
			}
			IAnswerListener	listener	=	 UserListeners.getAnswerListener(message.getFrom().getId());
			if(listener	!=	null) {
				return listener.action(message);
			}
		}catch(Exception e) {
			log.error("getAnswer: {}",e);
		}
		
		return new DefaultExecuter().getErrorAnswer(message,message.getText());
	}

	
}
