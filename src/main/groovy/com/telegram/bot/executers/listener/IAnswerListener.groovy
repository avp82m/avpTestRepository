package com.telegram.bot.executers.listener

import org.apache.camel.component.telegram.model.IncomingMessage
import org.apache.camel.component.telegram.model.OutgoingTextMessage

interface IAnswerListener {
	OutgoingTextMessage action(IncomingMessage message)
}
