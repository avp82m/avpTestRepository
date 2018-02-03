package com.telegram.bot.executers.command

import java.util.HashMap
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message
import com.telegram.bot.cache.CacheManager
import com.telegram.bot.entity.FriendUser
import com.telegram.bot.executers.AccessLevels
import com.telegram.bot.executers.IExecuters

class LogoutExecuter implements IExecuters {
	private Message message	=	null;
	private SendMessage outMessage	=	new SendMessage();
	
	@Override
	public void setMessage(Message message) {
		this.message=message;
	}
	
	@Override
	public SendMessage getAnswer() {
		FriendUser friendUser	=	CacheManager.removeUserFromCache(message.getFrom().getId());
		if(friendUser	!=	null) {
			outMessage.setText("До свидания "+friendUser.getFname()+" "+friendUser.getName()+"!");
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
