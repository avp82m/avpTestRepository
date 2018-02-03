package friend.bot.executers.command


import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message

import friend.bot.executers.AccessLevels
import friend.bot.executers.IExecuters


class DefaultExecuter implements IExecuters {
	private Message message	=	null;
	private SendMessage outMessage	=	new SendMessage();
	
	@Override
	public void setMessage(Message message) {
		this.message=message;
	}

	
	@Override
	public SendMessage getAnswer() {
		outMessage.setText("Не понимаю Вас\n. Воспользуйтесь командой /help для получения списка доступных команд.");
		return outMessage;
	}
	
	
	@Override
	public String getHelp() {
		return "";
	}
	

	public SendMessage getErrorAnswer(Message message,String error) {
		outMessage.setText(error);
		return outMessage;
	}


	@Override
	public AccessLevels getAccessLevel() {
		return AccessLevels.ALL;
	}

}
