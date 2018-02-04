package friend.bot.telegram.api

import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText

class DeleteMessageAction implements IAction{
	private DeleteMessage message	=	null;
	
	DeleteMessageAction(Long chatId,Integer messageId){
		message	=	new DeleteMessage(chatId,messageId)
	}
	
	@Override
	public DeleteMessage getDeleteMessage() {
		return message;
	}
	
	@Override
	public ActionTypes getType() {
		return ActionTypes.DELETE;
	}

	@Override
	public SendMessage getSendMessage() {
		return null;
	}

	@Override
	public EditMessageText getEditMessage() {
		return null;
	}

}
