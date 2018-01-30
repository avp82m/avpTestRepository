package com.telegram.bot.executers.command

import java.util.HashMap

import org.apache.camel.component.telegram.model.IncomingMessage
import org.apache.camel.component.telegram.model.OutgoingTextMessage
import com.telegram.bot.cache.CacheManager
import com.telegram.bot.entity.FriendUser
import com.telegram.bot.executers.AccessLevels
import com.telegram.bot.executers.IExecuters

class LogoutExecuter implements IExecuters {
	private IncomingMessage message	=	null;
	private OutgoingTextMessage outMessage	=	new OutgoingTextMessage();
	
	@Override
	public void setMessage(IncomingMessage message) {
		this.message=message;
	}
	
	@Override
	public OutgoingTextMessage getAnswer() {
		if(null!=CacheManager.removeUserFromCache(message.getFrom().getId())) {
			outMessage.setText("Пользователь вышел.");
		}else {
			outMessage.setText("Выход не требуется, пользователь не авторизован.");
		}
		return outMessage;
	}


	@Override
	public String getHelp() {
		return "Выход";
	}

	@Override
	public AccessLevels getAccessLevel() {
		return AccessLevels.AUTHORIZED;
	}

}
