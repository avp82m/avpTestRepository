package com.telegram.bot.executers.command

import java.util.HashMap

import org.apache.camel.component.telegram.model.IncomingMessage
import org.apache.camel.component.telegram.model.OutgoingTextMessage
import org.springframework.beans.factory.annotation.Autowired

import com.telegram.bot.cache.CacheManager
import com.telegram.bot.entity.FriendUser
import com.telegram.bot.executers.IExecuters
import com.telegram.bot.executers.listener.AnswerListeners
import com.telegram.bot.executers.listener.IAnswerListener
import com.telegram.bot.frendserever.api.FrendServerAPI
import com.telegram.bot.service.MessageService

class LoginExecuter implements IExecuters {
	private IncomingMessage message	=	null;
	private OutgoingTextMessage outMessage	=	new OutgoingTextMessage();
	private FriendUser friendUser	=	new FriendUser();
	
//	@Autowired
//	MessageService messageService;
	
	@Override
	public void setMessage(IncomingMessage message) {
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
		OutgoingTextMessage action(IncomingMessage message) {
			
			friendUser.setId(message.from.getId());
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
		OutgoingTextMessage action(IncomingMessage message) {
			friendUser	=	new FriendUser(message.getFrom().getId());
			friendUser.setPin(message.getText());
//			messageService.remove(message);
			if(checkPin(message.getText())) {
				outMessage.setText("ПИН успешно подтвержден. Пользователь авторизован.");
				friendUser.setIsAutorized(true);
			}else {
				outMessage.setText("ПИН не подтвержден. Повторите процедуру авторизации /login.");
				friendUser.setIsAutorized(false);
			}
			friendUser.save();
			return outMessage;
		}
	}
	
	@Override
	public OutgoingTextMessage getAnswer() {
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

	
}
