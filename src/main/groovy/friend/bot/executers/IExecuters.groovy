package friend.bot.executers

import org.telegram.telegrambots.api.objects.Message;

import org.telegram.telegrambots.api.methods.send.SendMessage

interface IExecuters {
	SendMessage getAnswer();
	void setMessage(Message message);
	String getHelp();
	AccessLevels getAccessLevel();
}
