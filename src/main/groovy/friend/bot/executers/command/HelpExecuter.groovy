package friend.bot.executers.command

import java.util.HashMap
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message

import friend.bot.entity.FriendUser
import friend.bot.executers.AccessLevels
import friend.bot.executers.ExecutersFactory
import friend.bot.executers.IExecuters

class HelpExecuter implements IExecuters{
	private Message message	=	null;
	private SendMessage outMessage	=	new SendMessage();
	
	
	@Override
	public void setMessage(Message message) {
		this.message=message;
	}
	
	@Override
	public SendMessage getAnswer() {
		String a	=	"";
		FriendUser friendUser	=	new FriendUser(message.getFrom().getId());
		HashMap<String,IExecuters>botCommands	=	new ExecutersFactory().getRegisterExecuters();
		botCommands.keySet().each(){k->
			if (AccessLevels.ALL==botCommands.get(k).getAccessLevel()) {
				a	=	a	+	"\n"	+	k	+	" - "	+	botCommands.get(k).getHelp();
			}else if(AccessLevels.ANONYMOUS==botCommands.get(k).getAccessLevel()
						&&!friendUser.getIsAutorized()) {
				a	=	a	+	"\n"	+	k	+	" - "	+	botCommands.get(k).getHelp();
			}else if(AccessLevels.AUTHORIZED ==botCommands.get(k).getAccessLevel()
						&&friendUser.getIsAutorized()) {
				a	=	a	+	"\n"	+	k	+	" - "	+	botCommands.get(k).getHelp();
			}
		}
		outMessage.setText(a);
		return outMessage;
	}

	@Override
	public String getHelp() {
		return "Получение списка доступных команд";
	}

	@Override
	public AccessLevels getAccessLevel() {
		return AccessLevels.ALL;
	}
}
