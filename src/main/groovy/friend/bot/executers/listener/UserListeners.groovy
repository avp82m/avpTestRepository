package friend.bot.executers.listener

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class UserListeners {
	private static HashMap<Long,IAnswerListener> AnswerListeners	=	new HashMap<Long,IAnswerListener>();
	private static HashMap<Long,ICallbackListener> CallbackListeners	=	new HashMap<Long,ICallbackListener>();
	
	private static final Logger log = LoggerFactory.getLogger(UserListeners.class);
	
	public static void newAnswerListener(Long userId,IAnswerListener listener) {
		log.debug("Включаем AnswerListener: {} тип: {}",userId,listener.class.toString());
		AnswerListeners.put(userId,listener);
	}
	
	public static IAnswerListener getAnswerListener(Long userId) {
		log.debug("Ищем AnswerListeners для {}",userId);
		IAnswerListener resultAction	=	AnswerListeners.remove(userId);
		if(resultAction!=null)
			log.debug("Сработал AnswerListeners : {}",resultAction.class);
		else
			log.debug("AnswerListeners не найден");
			
		return resultAction;
	}
	
	
	public static void newCallbackListener(Long userId,ICallbackListener listener) {
		log.debug("Включаем CallbackListener: {} тип: {}",userId,listener.class.toString());
		CallbackListeners.put(userId,listener);
	}
	
	public static ICallbackListener getCallbackListener(Long userId) {
		log.debug("Ищем CallbackListener для {}",userId);
		ICallbackListener resultAction	=	CallbackListeners.remove(userId);
		if(resultAction!=null)
			log.debug("Сработал CallbackListener : {}",resultAction.class);
		else
			log.debug("CallbackListener не найден");
			
		return resultAction;
	}
	
	
	public static void clear(Long userId) {
		log.debug("Очищаем все listeners пользователя: {} ",userId);
		clearAnswerListeners(userId);
		clearCallBackListeners(userId);
	}
	
	public static void clearAnswerListeners(Long userId) {
		log.debug("Очищаем все AnswerListeners пользователя: {} ",userId);
		AnswerListeners.remove(userId);
	}
	
	public static void clearCallBackListeners(Long userId) {
		log.debug("Очищаем все CallBackListeners пользователя: {} ",userId);
		CallbackListeners.remove(userId);
	}
}
