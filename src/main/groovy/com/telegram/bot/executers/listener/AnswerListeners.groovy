package com.telegram.bot.executers.listener

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AnswerListeners {
	private static HashMap<Long,IAnswerListener> listeners	=	new HashMap<Long,IAnswerListener>();
	private static final Logger log = LoggerFactory.getLogger(AnswerListeners.class);
	
	public static void createNewListener(Long userId,IAnswerListener listener) {
		log.debug("Включаем ожидание ответа от пользователя: {} тип: {}",userId,listener.class.toString());
		listeners.put(userId,listener);
	}
	
	public static IAnswerListener getListener(Long userId) {
		return listeners.remove(userId) ;
	}
	
}
