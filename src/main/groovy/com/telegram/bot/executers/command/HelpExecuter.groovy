package com.telegram.bot.executers.command

import java.util.HashMap

import org.apache.camel.component.telegram.model.IncomingMessage
import org.apache.camel.component.telegram.model.OutgoingTextMessage

import com.telegram.bot.executers.ExecutersFactory
import com.telegram.bot.executers.IExecuters

class HelpExecuter implements IExecuters{
	private IncomingMessage message	=	null;
	private OutgoingTextMessage outMessage	=	new OutgoingTextMessage();
	
	
	@Override
	public void setMessage(IncomingMessage message) {
		this.message=message;
	}
	
	@Override
	public OutgoingTextMessage getAnswer() {
		String a	=	"";
		HashMap<String,IExecuters>botCommands	=	new ExecutersFactory().getRegisterExecuters();
		botCommands.keySet().each(){k->
			a	=	a	+	"\n"	+	k	+	" - "	+	botCommands.get(k).getHelp();
		}
		outMessage.setText(a);
		return outMessage;
	}

	@Override
	public String getHelp() {
		return "Получение списка доступных команд";
	}

}
