package friend.bot.executers.command

import java.util.HashMap
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message

import friend.bot.cache.CacheManager
import friend.bot.entity.FriendUser
import friend.bot.executers.AccessLevels
import friend.bot.executers.IExecuters

class StartExecuter implements IExecuters {
	private Message message	=	null;
	private SendMessage outMessage	=	new SendMessage();
	private FriendUser friendUser	=	null;
	
	@Override
	public void setMessage(Message message) {
		this.message=message;
		this.friendUser	=	new FriendUser(message.getFrom().getId());
	}
	
	@Override
	public SendMessage getAnswer() {
		if(friendUser == null || !friendUser.getIsAutorized())
			outMessage.setText("Здравствуйте! Авторизуйтесь, что бы я знал как к Вам обращаться /login.");
		else
			outMessage.setText("Здравствуйте "+friendUser.getFname()+" "+friendUser.getName()+"!\nОзнакомьтесь со списком доступных Вам комманд /help.");
			
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
