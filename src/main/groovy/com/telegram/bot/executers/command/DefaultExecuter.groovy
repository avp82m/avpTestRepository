package com.telegram.bot.executers.command

import org.apache.camel.component.telegram.model.IncomingMessage
import org.apache.camel.component.telegram.model.OutgoingTextMessage
import com.telegram.bot.executers.IExecuters

class DefaultExecuter implements IExecuters {
	private IncomingMessage message	=	null;
	private OutgoingTextMessage outMessage	=	new OutgoingTextMessage();
	
	@Override
	public void setMessage(IncomingMessage message) {
		this.message=message;
	}

	
	@Override
	public OutgoingTextMessage getAnswer() {
		outMessage.setText("Не понимаю Вас\n. Воспользуйтесь командой /help для получения списка доступных команд.");
		return outMessage;
	}
	
	
	@Override
	public String getHelp() {
		return "";
	}
	

	public OutgoingTextMessage getErrorAnswer(IncomingMessage message,String error) {
		outMessage.setText(error);
		return outMessage;
	}

}
