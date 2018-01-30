package com.telegram.bot.service

import org.springframework.stereotype.Component
import com.telegram.bot.cache.CacheManager
import com.telegram.bot.entity.FriendUser

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	private String getUserEmail(Long userId) {
		
	}
	
	
	
	public FriendUser Auth(Long userId) {
		log.debug("Проверяем ИД пользователя: {}",userId);
		return new FriendUser(userId);
	}
}
