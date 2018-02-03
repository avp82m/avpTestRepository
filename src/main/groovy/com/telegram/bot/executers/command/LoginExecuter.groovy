package com.telegram.bot.executers.command

import java.util.HashMap


import org.springframework.beans.factory.annotation.Autowired
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message
import com.telegram.bot.cache.CacheManager
import com.telegram.bot.entity.FriendUser
import com.telegram.bot.executers.AccessLevels
import com.telegram.bot.executers.IExecuters
import com.telegram.bot.executers.listener.AnswerListeners
import com.telegram.bot.executers.listener.IAnswerListener
import com.telegram.bot.frendserever.api.FrendServerAPI
import com.telegram.bot.frendserever.api.requests.GetCurrentUser
import com.telegram.bot.frendserever.api.requests.IFriendServerRequest

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
	
	private Boolean checkPin(String pin) {
		if(FrendServerAPI.authenticate(friendUser)) {
			return true
		}
		
		return false;
	}	
	
	class EmailListener implements IAnswerListener  {
		private FriendUser friendUser	=	new FriendUser();
		
		@Override
		SendMessage action(Message message) {
			
			friendUser.setId(message.getFrom().getId());
			friendUser.setEmail(message.getText().toLowerCase());
			
			if(checkEmail(friendUser.getEmail())) {
				outMessage.setText("Введите полученный ПИН:");
				AnswerListeners.createNewListener(friendUser.getId(), new PinListener());
				friendUser.save();
			}else {
				outMessage.setText("Емайл не подтвержден. Повторите процедуру авторизации /login.");
			}
			
			return outMessage;
		}
	}
	
	class PinListener implements IAnswerListener  {
		@Override
		SendMessage action(Message message) {
			friendUser	=	new FriendUser(message.getFrom().getId());
			friendUser.setPin(message.getText());
			if(checkPin(message.getText())) {
				outMessage.setText("ПИН успешно подтвержден. Пользователь авторизован.");
				friendUser.setIsAutorized(true);
				def user	=	FrendServerAPI.sendRequest(new GetCurrentUser(), friendUser);
				friendUser.refresh(user);
			}else {
				outMessage.setText("ПИН не подтвержден. Повторите процедуру авторизации /login.");
				friendUser.setIsAutorized(false);
			}
			friendUser.save();
			return outMessage;
		}
	}
	
	@Override
	public SendMessage getAnswer() {
		friendUser	=	new FriendUser(message.getFrom().getId());
		if(friendUser == null || !friendUser.getIsAutorized()) {
			outMessage.setText("Пользователь не определен, введите email:");
			AnswerListeners.createNewListener(message.getFrom().getId(), new EmailListener());
		}else {
			outMessage.setText("Пользователь уже авторизован...");
		}
		return outMessage;
	}

	@Override
	public String getHelp() {
		return "Авторизация";
	}

	@Override
	public AccessLevels getAccessLevel() {
		return AccessLevels.ANONYMOUS;
	}
}
