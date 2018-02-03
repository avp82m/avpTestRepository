package friend.bot.executers.listener

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AnswerListeners {
	private static HashMap<Integer,IAnswerListener> listeners	=	new HashMap<Integer,IAnswerListener>();
	private static final Logger log = LoggerFactory.getLogger(AnswerListeners.class);
	
	public static void createNewListener(Integer userId,IAnswerListener listener) {
		log.debug("Включаем ожидание ответа от пользователя: {} тип: {}",userId,listener.class.toString());
		listeners.put(userId,listener);
	}
	
	public static IAnswerListener getListener(Integer userId) {
		return listeners.remove(userId) ;
	}
	
}
