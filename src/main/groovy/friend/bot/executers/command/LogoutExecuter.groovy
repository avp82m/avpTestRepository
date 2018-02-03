package friend.bot.executers.command

import java.util.HashMap
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message

import friend.bot.cache.CacheManager
import friend.bot.entity.FriendUser
import friend.bot.executers.AccessLevels
import friend.bot.executers.IExecuters

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
