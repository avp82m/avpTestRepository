package friend.bot.executers.command

import java.util.HashMap

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message

import friend.bot.cache.CacheManager
import friend.bot.entity.FriendUser
import friend.bot.executers.AccessLevels
import friend.bot.executers.IExecuters

class LogoutExecuter implements IExecuters {
	private Message message	=	null;
	private SendMessage outMessage	=	new SendMessage();
	private static final Logger log = LoggerFactory.getLogger(LogoutExecuter.class);
	
	@Override
	public void setMessage(Message message) {
		this.message=message;
	}
	
	public static FriendUser logout(Long userId) {
		log.debug("Пользователь {} выходит",userId);
		return CacheManager.removeUserFromCache(userId);
	}
	
	@Override
	public SendMessage getAnswer() {
		FriendUser friendUser	=	logout(message.getFrom().getId().toLong())
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
