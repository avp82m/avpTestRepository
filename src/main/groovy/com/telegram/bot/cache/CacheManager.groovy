package com.telegram.bot.cache


import com.telegram.bot.entity.FriendUser

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class CacheManager {
       private static final Logger log = LoggerFactory.getLogger(CacheManager.class);
	   private static HashMap <Integer,FriendUser> FriendUsersCache = new HashMap <Integer,FriendUser>();
	   
	   
       public static FriendUser getUserFromCache(Integer userId) {
		   log.debug("Ищем пользователя в кеше: {}",userId);
		   if(FriendUsersCache.containsKey(userId)) {
			   log.debug("Извлекаем пользователя из кеша: {}",userId);
			   return FriendUsersCache.get(userId);
		   }
		   
		   log.debug("Пользователь не найден в кеше: {}",userId);
		   return null;
	   }
	   
	   
	   public static HashMap <Integer,FriendUser> getUsers() {
		   return FriendUsersCache;
	   }
	   
	   
	   public static void setUserToCache(Integer userId,FriendUser) {
		   log.debug("Добавляем пользователя в кеш: {}",userId);
		   FriendUsersCache.put(userId,FriendUser);
	   }
	   
	   
	   public static FriendUser removeUserFromCache(Integer userId) {
		   log.debug("Удаляем пользователя из кеша: {}",userId);
		   return FriendUsersCache.remove(userId);
	   }
       
       
       
}


