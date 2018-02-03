package friend.bot.executers.command

import java.util.HashMap

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message;

import friend.bot.cache.CacheManager
import friend.bot.entity.FriendUser
import friend.bot.executers.AccessLevels
import friend.bot.executers.IExecuters
import friend.bot.frendserever.api.FrendServerAPI
import friend.bot.frendserever.api.requests.GetCurrentUser


class AboutMeExecuter implements IExecuters {
	private static final Logger log = LoggerFactory.getLogger(AboutMeExecuter.class);
	private Message message	=	null;
	private SendMessage outMessage	=	new SendMessage();
	
	
	@Override
	public void setMessage(Message message) {
		this.message=message;
	}
	
	@Override
	public SendMessage getAnswer() {
		String answer	=	"";
		try {
			FriendUser friendUser	=	new FriendUser(message.getFrom().getId());
			
			if (friendUser.getIsAutorized()	==	false) {
				outMessage.setText("Для получения информации необходимо авторизоваться /login");
				return outMessage;
			}
			
			
			
			def user	=	FrendServerAPI.sendRequest(new GetCurrentUser(), friendUser);
			friendUser.refresh(user);
			if(user!=null) {
				answer	=	"<b>ФИО: </b>"+friendUser.getUserData().kadr.fio.toString()+
							"\n<b>Табельный номер: </b>"+friendUser.getUserData().kadr.tabNum.toString()+
							"\n<b>Email: </b>"+friendUser.getUserData().ldap.eMail.toString()+
							"\n<b>Должность: </b>"+friendUser.getUserData().kadr.position.toString()+
							"\n<b>Кадровая система: </b>"+friendUser.getUserData().systemKadr.toString();
			}else {
				throw new Exception("User is null");	
			}
		}catch(Exception e) {
			answer	=	"Информация о пользователе не получена";
			log.debug("Exception: {}",e);
		}	
		outMessage.setText(answer);
		outMessage.setParseMode("html");
		return outMessage;
	}


	@Override
	public String getHelp() {
		return "Информация о пользователе";
	}

	@Override
	public AccessLevels getAccessLevel() {
		return AccessLevels.AUTHORIZED;
	}

}
