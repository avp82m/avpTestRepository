package com.telegram.bot.executers.command

import java.util.HashMap

import org.apache.camel.component.telegram.model.IncomingMessage
import org.apache.camel.component.telegram.model.OutgoingTextMessage
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telegram.bot.cache.CacheManager
import com.telegram.bot.entity.FriendUser
import com.telegram.bot.executers.IExecuters
import com.telegram.bot.frendserever.api.FrendServerAPI


class AboutMeExecuter implements IExecuters {
	private static final Logger log = LoggerFactory.getLogger(AboutMeExecuter.class);
	private IncomingMessage message	=	null;
	private OutgoingTextMessage outMessage	=	new OutgoingTextMessage();
	
	@Override
	public void setMessage(IncomingMessage message) {
		this.message=message;
	}
	
	@Override
	public OutgoingTextMessage getAnswer() {
		String answer	=	"";
		try {
			def user = FrendServerAPI.getCurrentUser(new FriendUser(message.getFrom().getId()))
			if(user!=null) {
				answer	=	"<b>ФИО: </b>"+user.object.kadr.fio.toString()+
							"\n<b>Табельный номер: </b>"+user.object.kadr.tabNum.toString()+
							"\n<b>Email: </b>"+user.object.ldap.eMail.toString()+
							"\n<b>Должность: </b>"+user.object.kadr.position.toString()+
							"\n<b>Кадровая системва: </b>"+user.object.systemKadr.toString();
			}else {
				throw new Exception("User is null");	
			}
		}catch(Exception e) {
			answer	=	"Информация о пользователе не получена";
			log.debug("Exception: {}",e);
		}	
		outMessage.setText(answer);
		return outMessage;
	}


	@Override
	public String getHelp() {
		return "Информация о пользователе";
	}

}
