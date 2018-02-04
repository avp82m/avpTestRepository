package friend.bot.telegram.api

import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText

interface IAction {
	ActionTypes getType();
	DeleteMessage getDeleteMessage();
	SendMessage getSendMessage();
	EditMessageText getEditMessage();
}
