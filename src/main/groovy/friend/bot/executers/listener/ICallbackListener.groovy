package friend.bot.executers.listener

import friend.bot.telegram.api.Response
import org.telegram.telegrambots.api.objects.CallbackQuery
import org.telegram.telegrambots.api.objects.Message

interface ICallbackListener {
	Response action(CallbackQuery callback)
}
