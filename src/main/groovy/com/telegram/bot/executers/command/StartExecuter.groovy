package com.telegram.bot.executers.command

import java.util.HashMap

import org.apache.camel.component.telegram.model.IncomingMessage
import org.apache.camel.component.telegram.model.OutgoingTextMessage
import com.telegram.bot.entity.FriendUser
import com.telegram.bot.executers.AccessLevels
import com.telegram.bot.executers.IExecuters
import com.telegram.bot.cache.CacheManager

class StartExecuter implements IExecuters {
	private IncomingMessage message	=	null;
	private OutgoingTextMessage outMessage	=	new OutgoingTextMessage();
	private FriendUser friendUser	=	null;
	
	@Override
	public void setMessage(IncomingMessage message) {
		this.message=message;
		this.friendUser	=	new FriendUser(message.from.getId());
	}
	
	@Override
	public OutgoingTextMessage getAnswer() {
		if(friendUser == null || !friendUser.getIsAutorized())
			outMessage.setText("Здравствуйте! Авторизуйтесь, что бы я знал с кем говорю /login.");
		else
			outMessage.setText("Здравствуйте! Вы авторизованы("+friendUser.getSessionId()+").\nОзнакомьтесь со списком доступных комманд /help.");
			
		return outMessage;
	}


	@Override
	public String getHelp() {
		return "Вывод привествия";
	}

	@Override
	public AccessLevels getAccessLevel() {
		return AccessLevels.AUTHORIZED;
	}
}
