package friend.bot.telegram.api

import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText

class SendMessageAction implements IAction{
	private SendMessage message	=	null;
	
	SendMessageAction(SendMessage message){
		this.message	=	message;
	}
	
	@Override
	public SendMessage getSendMessage() {
		return message;
	}
	
	@Override
	public ActionTypes getType() {
		return ActionTypes.SENDMESSAGE;
	}

	@Override
	public DeleteMessage getDeleteMessage() {
		return null;
	}

	@Override
	public EditMessageText getEditMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
