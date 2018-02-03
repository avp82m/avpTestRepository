package com.telegram.bot.executers.listener

import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message

interface IAnswerListener {
	SendMessage action(Message message)
}
