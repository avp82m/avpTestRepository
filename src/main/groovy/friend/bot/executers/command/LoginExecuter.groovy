package friend.bot.executers.command

import java.util.HashMap

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.springframework.beans.factory.annotation.Autowired
import org.telegram.telegrambots.api.methods.BotApiMethod
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.api.objects.CallbackQuery
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.exceptions.TelegramApiException
import org.telegram.telegrambots.TelegramBotsApi
import friend.bot.BotInitializer
import friend.bot.cache.CacheManager
import friend.bot.entity.FriendUser
import friend.bot.executers.AccessLevels
import friend.bot.executers.IExecuters
import friend.bot.executers.listener.UserListeners
import friend.bot.executers.listener.IAnswerListener
import friend.bot.executers.listener.ICallbackListener
import friend.bot.frendserever.api.FrendServerAPI
import friend.bot.frendserever.api.requests.GetCurrentUser
import friend.bot.frendserever.api.requests.IFriendServerRequest
import friend.bot.telegram.api.DeleteMessageAction
import friend.bot.telegram.api.EditMessageAction
import friend.bot.telegram.api.Response
import friend.bot.telegram.api.SendMessageAction
import groovy.util.ObjectGraphBuilder.DefaultNewInstanceResolver

class LoginExecuter implements IExecuters {
	private Message message	=	null;
	private SendMessage outMessage	=	new SendMessage();
	private FriendUser friendUser	=	new FriendUser();

	@Override
	public void setMessage(Message message) {
		this.message=message;
	}


	private Boolean checkEmail(String email) {
		if(true) {
			return true;
		}else {
			return false;
		}
	}

	
	private InlineKeyboardMarkup getKeyBoard(){
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline4 = new ArrayList<>();
		List<InlineKeyboardButton> rowInline5 = new ArrayList<>();
		rowInline1.add(new InlineKeyboardButton().setText("1").setCallbackData("1"));
		rowInline1.add(new InlineKeyboardButton().setText("2").setCallbackData("2"));
		rowInline1.add(new InlineKeyboardButton().setText("3").setCallbackData("3"));
		rowInline2.add(new InlineKeyboardButton().setText("4").setCallbackData("4"));
		rowInline2.add(new InlineKeyboardButton().setText("5").setCallbackData("5"));
		rowInline2.add(new InlineKeyboardButton().setText("6").setCallbackData("6"));
		rowInline3.add(new InlineKeyboardButton().setText("7").setCallbackData("7"));
		rowInline3.add(new InlineKeyboardButton().setText("8").setCallbackData("8"));
		rowInline3.add(new InlineKeyboardButton().setText("9").setCallbackData("9"));
		rowInline4.add(new InlineKeyboardButton().setText("0").setCallbackData("0"));
		rowInline5.add(new InlineKeyboardButton().setText("Войти").setCallbackData("Войти"));
		rowInline5.add(new InlineKeyboardButton().setText("Отмена").setCallbackData("Отмена"));
		rowsInline.add(rowInline1);
		rowsInline.add(rowInline2);
		rowsInline.add(rowInline3);
		rowsInline.add(rowInline4);
		rowsInline.add(rowInline5);
		markupInline.setKeyboard(rowsInline);
		
		return markupInline;
	}
	
	
	class EmailListener implements IAnswerListener  {
		private FriendUser friendUser	=	new FriendUser();
		
		
		
		@Override
		SendMessage action(Message message) {

			friendUser.setId(message.getFrom().getId().toLong());
			friendUser.setEmail(message.getText().toLowerCase());

			if(checkEmail(friendUser.getEmail())) {
				outMessage.setText("ПИН:");
				outMessage.setReplyMarkup(getKeyBoard());
				UserListeners.newCallbackListener(message.getFrom().getId().toLong(), new PinListener());
				friendUser.save();
			}else {
				outMessage.setText("Емайл не подтвержден. Повторите процедуру входа /login.");
			}

			return outMessage;
		}
	}

	class PinListener implements ICallbackListener  {
		private Response response	=	new Response();
		
		private Boolean checkPin(CallbackQuery callback) {
			friendUser	=	new FriendUser(callback.getFrom().getId());
			outMessage	=	new SendMessage();
			if(FrendServerAPI.authenticate(friendUser)) {
				outMessage.setText("ПИН успешно подтвержден.\nВоспользуйтесь /help, для получения списка команд.");
				friendUser.setIsAutorized(true);
				def user	=	FrendServerAPI.sendRequest(new GetCurrentUser(), friendUser);
				friendUser.refresh(user);
			}else {
				outMessage.setText("ПИН не подтвержден. Повторите процедуру входа /login.");
				friendUser.setIsAutorized(false);
			}
			friendUser.save();
			response.add(new SendMessageAction(outMessage));
			deleteMessage(callback);
		}

		private void deleteMessage(CallbackQuery callback) {
			response.add(new DeleteMessageAction(callback.getMessage().getChatId(),callback.getMessage().getMessageId()));
		}

		private void addNumber(String number) {
			friendUser	=	new FriendUser(message.getFrom().getId());
			friendUser.setPin(friendUser.getPin()+number);
			friendUser.save();
			UserListeners.newCallbackListener(message.getFrom().getId().toLong(), new PinListener());
			
			EditMessageText editMessage	=	new EditMessageText();
			String viewPin	=	"";
			int i=1;
			for(char c:friendUser.getPin().value) {
				if (i<friendUser.getPin().value.size())
					viewPin	=	viewPin	+	" *";
				else
					viewPin	=	viewPin	+	" "	+	c;
				i++;
			}
			
			editMessage.setText("ПИН: "+viewPin);
			editMessage.setReplyMarkup(getKeyBoard());
			response.add(new EditMessageAction(editMessage));
		}






		@Override
		Response action(CallbackQuery callback) {
			switch(callback.getData() ) {
				case("1"):
					addNumber(callback.getData())
					break;
				case("2"):
					addNumber(callback.getData())
					break;
				case("3"):
					addNumber(callback.getData())
					break;
				case("4"):
					addNumber(callback.getData())
					break;
				case("5"):
					addNumber(callback.getData())
					break;
				case("6"):
					addNumber(callback.getData())
					break;
				case("7"):
					addNumber(callback.getData())
					break;
				case("8"):
					addNumber(callback.getData())
					break;
				case("9"):
					addNumber(callback.getData())
					break;
				case("0"):
					addNumber(callback.getData())
					break;
				case("Отмена"):
					outMessage	=	new SendMessage();
					outMessage.setText("Для входа воспользуйтесь командой /login.");
					response.add(new SendMessageAction(outMessage));
					deleteMessage(callback);
					break;
				case("Войти"):
					checkPin(callback);
					break;
				default:
					deleteMessage();
					break;
			}
			
			return response;
		}
	}

	@Override
	public SendMessage getAnswer() {
		friendUser	=	new FriendUser(message.getFrom().getId());
		if(friendUser == null || !friendUser.getIsAutorized()) {
			outMessage.setText("Введите email:");
			UserListeners.newAnswerListener(message.getFrom().getId(), new EmailListener());
		}else {
			outMessage.setText("Вход уже осуществлен...");
		}
		return outMessage;
	}

	@Override
	public String getHelp() {
		return "Вход";
	}

	@Override
	public AccessLevels getAccessLevel() {
		return AccessLevels.ANONYMOUS;
	}
}
