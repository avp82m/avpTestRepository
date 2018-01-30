package com.telegram.bot.executers

import org.apache.camel.component.telegram.model.IncomingMessage
import org.apache.camel.component.telegram.model.OutgoingTextMessage

interface IExecuters {
	OutgoingTextMessage getAnswer();
	void setMessage(IncomingMessage message);
	String getHelp();
	AccessLevels getAccessLevel();
}
