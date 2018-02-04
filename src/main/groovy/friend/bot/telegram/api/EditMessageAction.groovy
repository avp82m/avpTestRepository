package friend.bot.telegram.api

import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText

class EditMessageAction implements IAction{
	private EditMessageText message	=	null;
	
	EditMessageAction(EditMessageText message){
		this.message	=	message;
	}
	
	@Override
	public SendMessage getSendMessage() {
		return null;
	}
	
	@Override
	public ActionTypes getType() {
		return ActionTypes.EDITTEXT;
	}

	@Override
	public DeleteMessage getDeleteMessage() {
		return null;
	}

	@Override
	public EditMessageText getEditMessage() {
		return message;
	}

}
